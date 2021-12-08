package com.example.opengl3d;

public class Model {
    static final float[] cubeVertexData = { // XYZ CCW
            // Front face
            -1.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,

            // Right face
            1.0f, 1.0f, 1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, 1.0f, -1.0f,

            // Back face
            1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,

            // Left face
            -1.0f, 1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f,

            // Top face
            -1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, -1.0f,

            // Bottom face
            1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f,
    };
    static final float[] cubeColorData = { // RGBA
            // Front face (red)
            0.4f, 0.4f, 1.0f, 1.0f,
            0.4f, 0.4f, 1.0f, 1.0f,
            0.4f, 0.4f, 1.0f, 1.0f,
            0.4f, 0.4f, 1.0f, 1.0f,
            0.4f, 0.4f, 1.0f, 1.0f,
            0.4f, 0.4f, 1.0f, 1.0f,

            // Right face (green)
            0.4f, 0.4f, 1.0f, 1.0f,
            0.4f, 0.4f, 1.0f, 1.0f,
            0.4f, 0.4f, 1.0f, 1.0f,
            0.4f, 0.4f, 1.0f, 1.0f,
            0.4f, 0.4f, 1.0f, 1.0f,
            0.4f, 0.4f, 1.0f, 1.0f,

            // Back face (blue)
            0.4f, 0.4f, 1.0f, 1.0f,
            0.4f, 0.4f, 1.0f, 1.0f,
            0.4f, 0.4f, 1.0f, 1.0f,
            0.4f, 0.4f, 1.0f, 1.0f,
            0.4f, 0.4f, 1.0f, 1.0f,
            0.4f, 0.4f, 1.0f, 1.0f,

            // Left face (yellow)
            0.4f, 0.4f, 1.0f, 1.0f,
            0.4f, 0.4f, 1.0f, 1.0f,
            0.4f, 0.4f, 1.0f, 1.0f,
            0.4f, 0.4f, 1.0f, 1.0f,
            0.4f, 0.4f, 1.0f, 1.0f,
            0.4f, 0.4f, 1.0f, 1.0f,

            // Top face (cyan)
            0.4f, 0.4f, 1.0f, 1.0f,
            0.4f, 0.4f, 1.0f, 1.0f,
            0.4f, 0.4f, 1.0f, 1.0f,
            0.4f, 0.4f, 1.0f, 1.0f,
            0.4f, 0.4f, 1.0f, 1.0f,
            0.4f, 0.4f, 1.0f, 1.0f,

            // Bottom face (magenta)
            0.4f, 0.4f, 1.0f, 1.0f,
            0.4f, 0.4f, 1.0f, 1.0f,
            0.4f, 0.4f, 1.0f, 1.0f,
            0.4f, 0.4f, 1.0f, 1.0f,
            0.4f, 0.4f, 1.0f, 1.0f,
            0.4f, 0.4f, 1.0f, 1.0f
    };

    static final float[] cubeNormalData = {
            // Front face
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,

            // Right face
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,

            // Back face
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,

            // Left face
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,

            // Top face
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,

            // Bottom face
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f
    };

    static final float[] cubeTextureMapData = {
            // Front face
            0.0f, 0.0f,
            0.0f, 1.0f,
                    1.0f, 0.0f,
                    0.0f, 1.0f,
                    1.0f, 1.0f,
                    1.0f, 0.0f,

                    // Right face
                    0.0f, 0.0f,
                    0.0f, 1.0f,
                    1.0f, 0.0f,
                    0.0f, 1.0f,
                    1.0f, 1.0f,
                    1.0f, 0.0f,

                    // Back face
                    0.0f, 0.0f,
                    0.0f, 1.0f,
                    1.0f, 0.0f,
                    0.0f, 1.0f,
                    1.0f, 1.0f,
                    1.0f, 0.0f,

                    // Left face
                    0.0f, 0.0f,
                    0.0f, 1.0f,
                    1.0f, 0.0f,
                    0.0f, 1.0f,
                    1.0f, 1.0f,
                    1.0f, 0.0f,

                    // Top face
                    0.0f, 0.0f,
                    0.0f, 1.0f,
                    1.0f, 0.0f,
                    0.0f, 1.0f,
                    1.0f, 1.0f,
                    1.0f, 0.0f,

                    // Bottom face
                    0.0f, 0.0f,
                    0.0f, 1.0f,
                    1.0f, 0.0f,
                    0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f
    };

    protected static String getVertexShader()
    {
        final String vertexShader =
                "uniform mat4 u_MVPMatrix;      \n"+ // uniform = 모든 정점에게 동일하게 적용
                "uniform mat4 u_MVMatrix;       \n"+

                "attribute vec3 a_Position;     \n"+ // attribute = 각 정점마다 다름 (needs buffer)
                "attribute vec3 a_Normal;       \n"+
                "attribute vec2 a_TextureMap;   \n"+

                "varying vec3 v_Position;       \n"+ // varying = vertex 출력 -> fragment 입력
                "varying vec3 v_Normal;         \n"+
                "varying vec2 v_TextureMap;         \n"+

                "void main()                    \n"+
                "{                              \n"+
                "   v_Position = vec3(u_MVMatrix * vec4(a_Position,1.0));                \n"+
                "   v_TextureMap = a_TextureMap;                                                   \n"+
                "   v_Normal = vec3(u_MVMatrix * vec4(a_Normal, 0.0));                   \n"+
                "   gl_Position = u_MVPMatrix * vec4(a_Position,1.0);                    \n"+
                "}                                                                       \n";
        return vertexShader;
    }

    protected static String getFragmentShader()
    {
        final String fragmentShader =
                "#define LIGHTS 1               \n"+
                "precision mediump float;       \n"+

                "uniform vec3 u_LightPos0;      \n"+
                "uniform vec3 u_LightPos1;      \n"+
                "uniform vec3 u_ViewPos;        \n"+
                "uniform sampler2D u_Texture;   \n"+

                "varying vec3 v_Position;          \n"+
                "varying vec3 v_Normal;          \n"+
                "varying vec2 v_TextureMap;          \n"+

                "void main()                    \n"+
                "{                              \n"+
                "   float Sum = 0.4;                                                     \n"+ // ambient
                "   vec3 u_LightPos[LIGHTS];                                             \n"+
                "   u_LightPos[0] = u_LightPos0;                                         \n"+
                //"   u_LightPos[1] = u_LightPos1;                                         \n"+

                "   for(int i=0; i<LIGHTS; i++){                                                         \n"+
                "       vec3 lightVector = normalize(u_LightPos[i] - v_Position);                        \n"+
                "       vec3 reflectVector = reflect(-lightVector, v_Normal);                            \n"+
                "       vec3 viewVector = normalize(u_ViewPos - v_Position);                             \n"+
                "       vec3 halfVector = normalize(lightVector + viewVector);                           \n"+
                "       float distance = length(u_LightPos[i] - v_Position);                             \n"+

                "       float diffuse = max(dot(v_Normal, lightVector),0.0), toonDiffuse;                \n"+ // diffuse
                "       if(diffuse < 0.4) toonDiffuse = 0.0;                                            \n"+
                "       else if(diffuse < 0.41) toonDiffuse = smoothstep(0.5, 0.51, diffuse)*0.5;  \n"+
                "       else if(diffuse < 0.75) toonDiffuse = 0.5;                                      \n"+
                "       else if(diffuse < 0.76) toonDiffuse = 0.5+smoothstep(0.75, 0.76, diffuse)*0.5; \n"+
                "       else toonDiffuse = 1.0;                                                          \n"+

                //"       float specular = pow(max(dot(viewVector, reflectVector),0.0), 64.0);           \n"+ // specular
                "       float specular = pow(max(dot(v_Normal, halfVector),0.0), 64.0);                 \n"+
                "       float toonSpecular = smoothstep(0.05, 0.1, specular);                            \n"+

                "       float toonRim = pow(diffuse, 0.1) * (1.0 - dot(viewVector, v_Normal));           \n"+ // rim
                "       toonRim = 0.1*smoothstep(0.95, 0.97, toonRim);                                       \n"+

                //"       float attenuation = 1.0/(1.0+0.09*distance+0.032*distance*distance);           \n"+
                "       Sum += toonDiffuse + toonSpecular + toonRim;                                          \n"+ // multiply attenuation if want to
                "   }                                                                 \n"+
                "   gl_FragColor = vec4(1.0, 1.0, 1.0, 1.0) * Sum * texture2D(u_Texture, v_TextureMap);    \n"+
                "}                                                                       \n";
        return fragmentShader;
    }

}
