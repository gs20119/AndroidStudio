package com.example.opengl3d;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

public class MyGLRenderer implements GLSurfaceView.Renderer
{
    private static final String TAG = "MyGLRenderer";
    private final Context mActivityContext;

    private final int floatSize = 4;
    private final int vDimension = 3;
    private final int nDimension = 3;
    private final int cubeNum = 3;
    private final int pointNum = 2;
    private final int texNum = 3;
    private final int faceNum;
    private final int backFaceNum;

    private float[] modelMatrix = new float[16];
    private float[] viewMatrix = new float[16];
    private float[] projMatrix = new float[16];
    private float[] MVPMatrix = new float[16]; // 여러 가지 변환행렬

    private float[] lightModelMatrix = new float[16];
    private float[][] lightPos = new float[pointNum][];
    static float lightClock = 0.0f;
    static float[] backPosition = { 0.0f, -5.0f, 0.5f };
    static float[][] lightPositions = {
            { 0.0f, 2.5f, -4.0f },
            { 0.0f, -2.5f, -4.0f }
    };
    static float[][] cubePositions = {
            { 0.0f, 1.0f, -1.0f },
            { 0.87f, -0.5f, -1.0f },
            { -0.87f, -0.5f, -1.0f },
    };
    static float[][] cubeAngles = {
            { 1.0f, 1.0f, 0.0f },
            { 1.0f, 0.0f, 1.0f },
            { 0.0f, 1.0f, 1.0f },
    };
    static int[] resources = {
            R.drawable.reddice,
            R.drawable.bluedice,
            R.drawable.greendice,
    };

    private final FloatBuffer cubeVertexBuffer;
    private final FloatBuffer cubeNormalBuffer;
    private final FloatBuffer cubeTextureMapBuffer; // 모델 정보를 저장하는 버퍼 (모델 종류별로 만들기)

    private final FloatBuffer backVertexBuffer;
    private final FloatBuffer backNormalBuffer;
    private final FloatBuffer backTextureMapBuffer;

    private int MVPMatrixHandle;
    private int MVMatrixHandle;
    private int[] lightPositionHandle = new int[pointNum];
    private int vertexHandle;
    private int normalHandle;
    private int[] textureDataHandle = new int[texNum];
    private int backTextureDataHandle;
    private int textureShaderHandle;
    private int textureMapHandle;
    private int viewHandle; // attribute, uniform 변수 값들을 셰이더로 넘기는 핸들

    private int mainProgramHandle; // 셰이더 프로그램 담당 핸들
    private int backProgramHandle;

    private Random random = new Random();
    private long throwTime = 0;
    private long lastTime = 0, time;
    private float[] inAir = new float[cubeNum];
    private float[] angleInDegrees = { 45.0f, 90.0f, 135.0f };
    private boolean shakeDetected;
    public void Shake(){ shakeDetected = true; }

    public MyGLRenderer(final Context context) // 이곳에서 버퍼 초기화 후 모델 정보 인수인계
    {
        mActivityContext = context;
        ObjLoader mCube = new ObjLoader(context, "Dice0.obj", 0.01f);
        faceNum = mCube.numFaces;
        ObjLoader mBack = new ObjLoader(context, "backdrop_yellow.obj", 2.0f);
        backFaceNum = mBack.numFaces;

        cubeVertexBuffer = ByteBuffer.allocateDirect(mCube.vertexData.length * floatSize).order(ByteOrder.nativeOrder()).asFloatBuffer();
        cubeVertexBuffer.put(mCube.vertexData).position(0);

        cubeNormalBuffer = ByteBuffer.allocateDirect(mCube.normalData.length * floatSize).order(ByteOrder.nativeOrder()).asFloatBuffer();
        cubeNormalBuffer.put(mCube.normalData).position(0);

        cubeTextureMapBuffer = ByteBuffer.allocateDirect(mCube.textureMapData.length * floatSize).order(ByteOrder.nativeOrder()).asFloatBuffer();
        cubeTextureMapBuffer.put(mCube.textureMapData).position(0);

        backVertexBuffer = ByteBuffer.allocateDirect(mBack.vertexData.length * floatSize).order(ByteOrder.nativeOrder()).asFloatBuffer();
        backVertexBuffer.put(mBack.vertexData).position(0);

        backNormalBuffer = ByteBuffer.allocateDirect(mBack.normalData.length * floatSize).order(ByteOrder.nativeOrder()).asFloatBuffer();
        backNormalBuffer.put(mBack.normalData).position(0);

        backTextureMapBuffer = ByteBuffer.allocateDirect(mBack.textureMapData.length * floatSize).order(ByteOrder.nativeOrder()).asFloatBuffer();
        backTextureMapBuffer.put(mBack.textureMapData).position(0);

    }


    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) // 화면 전처리 (한번만 실행)
    {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // 배경 검은색으로 칠하기
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST); // 은면제거와 깊이처리 허용

        final String vertexShader = Model.getVertexShader();
        final String fragmentShader = Model.getFragmentShader(); // 셰이더 코드
        final String backFragmentShader = Model.getBackFragmentShader();

        final int vertexShaderHandle = compileShader(GLES20.GL_VERTEX_SHADER, vertexShader);
        final int fragmentShaderHandle = compileShader(GLES20.GL_FRAGMENT_SHADER, fragmentShader); // 핸들에게 셰이더 컴파일 맡김
        final int backFragmentShaderHandle = compileShader(GLES20.GL_FRAGMENT_SHADER, backFragmentShader);
        mainProgramHandle = createAndLinkProgram(vertexShaderHandle, fragmentShaderHandle, new String[] {"a_Position", "a_Normal", "a_TextureMap"});
        backProgramHandle = createAndLinkProgram(vertexShaderHandle, backFragmentShaderHandle, new String[]{"a_Position", "a_Normal", "a_TextureMap"});
        // 셰이더 뭉쳐서 mainProgram 생성하고 컴파일 (일반 도형 렌더링용 프로그램)

        for(int i=0; i<texNum; i++)
            textureDataHandle[i] = loadTexture(mActivityContext, resources[i]);
        backTextureDataHandle = loadTexture(mActivityContext, R.drawable.yellow);
    }


    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) // 창이 바뀌었을 때 (간단)
    {
        GLES20.glViewport(0, 0, width, height);

        final float ratio = (float) width / height;
        final float left = -ratio;
        final float right = ratio;
        final float bottom = -1.0f;
        final float top = 1.0f;
        final float near = 1.0f;
        final float far = 30.0f;

        Matrix.frustumM(projMatrix, 0, left, right, bottom, top, near, far); // Proj Matrix (스크린 사영)
    }


    @Override
    public void onDrawFrame(GL10 glUnused)
    {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        time = SystemClock.uptimeMillis();
        if(shakeDetected){
            shakeDetected = false;
            throwTime = time;
            for(int i=0; i<cubeNum; i++){
                inAir[i] = 2000.0f+2000.0f*random.nextFloat();
                for(int j=0; j<vDimension; j++)
                    cubeAngles[i][j] = random.nextFloat()*10.0f-5.0f;
            }
        }
        for(int i=0; i<cubeNum; i++) if(time-throwTime<inAir[i]){
            angleInDegrees[i] += (360.0f/1000.0f)*(time-lastTime);
            while(angleInDegrees[i]>=360) angleInDegrees[i] -= 360.0f;
            lightClock += 0.0002f*angleInDegrees[i];
        }lastTime = time;
        while(lightClock>=360) lightClock -= 360.0f;
        lightPositions[0][0] = 4.5f * (float)Math.sin(lightClock);
        lightPositions[0][1] = 4.5f * (float)Math.cos(lightClock);

        float eyeX = 0.0f;
        float eyeY = 0.0f;
        float eyeZ = -5.0f; // 카메라 위치

        float lookX = 0.0f;
        float lookY = 0.0f;
        float lookZ = 1.0f; // 목표물 위치

        float upX = 0.0f;
        float upY = 1.0f;
        float upZ = 0.0f; // 위 방향

        Matrix.setLookAtM(viewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ); // view Matrix


        GLES20.glUseProgram(mainProgramHandle); // mainProgram 사용으로 넘어가기

        MVPMatrixHandle = GLES20.glGetUniformLocation(mainProgramHandle, "u_MVPMatrix"); // 셰이더의 변수랑 연동할 핸들 생성
        MVMatrixHandle = GLES20.glGetUniformLocation(mainProgramHandle, "u_MVMatrix"); // 핸들을 통해 변수에 값 넘겨줌
        viewHandle = GLES20.glGetUniformLocation(mainProgramHandle, "u_ViewPos");
        textureShaderHandle = GLES20.glGetUniformLocation(mainProgramHandle, "u_Texture");
        vertexHandle = GLES20.glGetAttribLocation(mainProgramHandle, "a_Position");
        normalHandle = GLES20.glGetAttribLocation(mainProgramHandle, "a_Normal");
        textureMapHandle = GLES20.glGetAttribLocation(mainProgramHandle, "a_TextureMap");
        for(int i=0; i<pointNum; i++) lightPositionHandle[i] = GLES20.glGetUniformLocation(mainProgramHandle, "u_LightPos"+Integer.toString(i));

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glUniform1i(textureShaderHandle, 0);

        for(int i=0; i<pointNum; i++){
            Matrix.setIdentityM(lightModelMatrix, 0);
            Matrix.translateM(lightModelMatrix, 0, lightPositions[i][0], lightPositions[i][1], lightPositions[i][2]);
            lightPos[i] = new float[] { 0.0f, 0.0f, 0.0f, 1.0f };
            Matrix.multiplyMV(lightPos[i], 0, lightModelMatrix, 0, lightPos[i], 0);
            Matrix.multiplyMV(lightPos[i], 0, viewMatrix, 0, lightPos[i], 0);
        }

        for(int i=0; i<cubeNum; i++){
            Matrix.setIdentityM(modelMatrix, 0);
            Matrix.translateM(modelMatrix, 0, cubePositions[i][0], cubePositions[i][1], cubePositions[i][2]); // 평행이동
            Matrix.rotateM(modelMatrix, 0, angleInDegrees[i], cubeAngles[i][0], cubeAngles[i][1], cubeAngles[i][2]); // 회전(크기와 방향)
            GLES20.glUniform3f(viewHandle, eyeX, eyeY, eyeZ);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureDataHandle[i]);
            drawCube();
        }


        GLES20.glUseProgram(backProgramHandle);

        MVPMatrixHandle = GLES20.glGetUniformLocation(backProgramHandle, "u_MVPMatrix"); // 셰이더의 변수랑 연동할 핸들 생성
        MVMatrixHandle = GLES20.glGetUniformLocation(backProgramHandle, "u_MVMatrix"); // 핸들을 통해 변수에 값 넘겨줌
        viewHandle = GLES20.glGetUniformLocation(backProgramHandle, "u_ViewPos");
        textureShaderHandle = GLES20.glGetUniformLocation(backProgramHandle, "u_Texture");
        vertexHandle = GLES20.glGetAttribLocation(backProgramHandle, "a_Position");
        normalHandle = GLES20.glGetAttribLocation(backProgramHandle, "a_Normal");
        textureMapHandle = GLES20.glGetAttribLocation(backProgramHandle, "a_TextureMap");
        for(int i=0; i<pointNum; i++) lightPositionHandle[i] = GLES20.glGetUniformLocation(backProgramHandle, "u_LightPos"+Integer.toString(i));

        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.translateM(modelMatrix, 0, backPosition[0], backPosition[1], backPosition[2]);
        Matrix.rotateM(modelMatrix, 0, 90.0f, 0.0f, 1.0f, 0.0f);
        GLES20.glUniform3f(viewHandle, eyeX, eyeY, eyeZ);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, backTextureDataHandle);
        drawBackground();

    }


    private void drawCube() // 오브젝트 그리기
    {
        // attribute 변수에 값 넘겨줄때.. (버퍼사용)
        cubeVertexBuffer.position(0); // 버퍼는 읽을때마다 0으로 만들어주기
        GLES20.glVertexAttribPointer(vertexHandle, vDimension, GLES20.GL_FLOAT, false, 0, cubeVertexBuffer); // ~핸들을 통해, ~버퍼에서 ~개씩 읽어와라
        GLES20.glEnableVertexAttribArray(vertexHandle); // 핸들이 버퍼에서 정보를 읽어오는걸 허용함

        cubeNormalBuffer.position(0);
        GLES20.glVertexAttribPointer(normalHandle, nDimension, GLES20.GL_FLOAT, false, 0, cubeNormalBuffer);
        GLES20.glEnableVertexAttribArray(normalHandle); // vertex, color, normal 에서 같은작업 진행

        cubeTextureMapBuffer.position(0);
        GLES20.glVertexAttribPointer(textureMapHandle, 2, GLES20.GL_FLOAT, false, 0, cubeTextureMapBuffer);
        GLES20.glEnableVertexAttribArray(textureMapHandle);

        // uniform 변수에 값 넘겨줄때.. (직접넣기)
        Matrix.multiplyMM(MVPMatrix, 0, viewMatrix, 0, modelMatrix, 0); // MV Matrix
        GLES20.glUniformMatrix4fv(MVMatrixHandle, 1, false, MVPMatrix, 0); // 셰이더에 넘겨주기
        Matrix.multiplyMM(MVPMatrix, 0, projMatrix, 0, MVPMatrix, 0); // MVP Matrix
        GLES20.glUniformMatrix4fv(MVPMatrixHandle, 1, false, MVPMatrix, 0); // 셰이더에 넘겨주기

        for(int i=0; i<pointNum; i++) GLES20.glUniform3f(lightPositionHandle[i], lightPos[i][0], lightPos[i][1], lightPos[i][2]);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, faceNum); // 36개의 삼각형 그리기
    }

    private void drawBackground()
    {
        // attribute 변수에 값 넘겨줄때.. (버퍼사용)
        backVertexBuffer.position(0); // 버퍼는 읽을때마다 0으로 만들어주기
        GLES20.glVertexAttribPointer(vertexHandle, vDimension, GLES20.GL_FLOAT, false, 0, backVertexBuffer); // ~핸들을 통해, ~버퍼에서 ~개씩 읽어와라
        GLES20.glEnableVertexAttribArray(vertexHandle); // 핸들이 버퍼에서 정보를 읽어오는걸 허용함

        backNormalBuffer.position(0);
        GLES20.glVertexAttribPointer(normalHandle, nDimension, GLES20.GL_FLOAT, false, 0, backNormalBuffer);
        GLES20.glEnableVertexAttribArray(normalHandle); // vertex, color, normal 에서 같은작업 진행

        backTextureMapBuffer.position(0);
        GLES20.glVertexAttribPointer(textureMapHandle, 2, GLES20.GL_FLOAT, false, 0, backTextureMapBuffer);
        GLES20.glEnableVertexAttribArray(textureMapHandle);

        // uniform 변수에 값 넘겨줄때.. (직접넣기)
        Matrix.multiplyMM(MVPMatrix, 0, viewMatrix, 0, modelMatrix, 0); // MV Matrix
        GLES20.glUniformMatrix4fv(MVMatrixHandle, 1, false, MVPMatrix, 0); // 셰이더에 넘겨주기
        Matrix.multiplyMM(MVPMatrix, 0, projMatrix, 0, MVPMatrix, 0); // MVP Matrix
        GLES20.glUniformMatrix4fv(MVPMatrixHandle, 1, false, MVPMatrix, 0); // 셰이더에 넘겨주기

        for(int i=0; i<pointNum; i++) GLES20.glUniform3f(lightPositionHandle[i], lightPos[i][0], lightPos[i][1], lightPos[i][2]);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, backFaceNum); // 36개의 삼각형 그리기
    }


    private int compileShader(final int shaderType, final String shaderSource)
    {
        int shaderHandle = GLES20.glCreateShader(shaderType); // 셰이더를 처음 생성하고 핸들을 남김
        if (shaderHandle != 0) // 핸들이 잘 생성되었다면
        {
            GLES20.glShaderSource(shaderHandle, shaderSource); // 셰이더에 셰이더 코드 입력
            GLES20.glCompileShader(shaderHandle); // 코드 컴파일
            final int[] compileStatus = new int[1];
            GLES20.glGetShaderiv(shaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

            if (compileStatus[0] == 0){ // 컴파일에 실패했다면 (예외처리)
                Log.e(TAG, "Error compiling shader: " + GLES20.glGetShaderInfoLog(shaderHandle));
                GLES20.glDeleteShader(shaderHandle);
                shaderHandle = 0;
            }
        }else throw new RuntimeException("Error creating shader.");
        return shaderHandle;
    }

    private int createAndLinkProgram(final int vertexShaderHandle, final int fragmentShaderHandle, final String[] attributes)
    {
        int programHandle = GLES20.glCreateProgram(); // 프로그램이 처음 생성되는 곳, 핸들을 남김
        if(programHandle != 0){ // 핸들이 잘 생성되었다면

            GLES20.glAttachShader(programHandle, vertexShaderHandle);
            GLES20.glAttachShader(programHandle, fragmentShaderHandle); // 셰이더와 프로그램 핸들끼리 연합
            final int size = (attributes != null) ? attributes.length : 0;
            for (int i = 0; i < size; i++) GLES20.glBindAttribLocation(programHandle, i, attributes[i]);

            GLES20.glLinkProgram(programHandle); // 핸들이 프로그램을 컴파일하기
            final int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(programHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);

            if(linkStatus[0] == 0){ // 링크에 실패했다면 (예외처리)
                Log.e(TAG, "Error compiling program: " + GLES20.glGetProgramInfoLog(programHandle));
                GLES20.glDeleteProgram(programHandle);
                programHandle = 0;
            }

        }else throw new RuntimeException("Error creating program.");
        return programHandle;
    }

    public static int loadTexture(final Context context, final int resourceId)
    {
        final int[] textureHandle = new int[1];
        GLES20.glGenTextures(1, textureHandle, 0);
        if(textureHandle[0] == 0){ throw new RuntimeException("Error generating texture name."); }

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;	// No pre-scaling

        final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();

        return textureHandle[0];
    }
}