package com.example.opengl3d;

public class Model {

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
                "       vec3 normalVector = normalize(v_Normal);                                         \n"+
                "       float distance = length(u_LightPos[i] - v_Position);                             \n"+

                "       float diffuse = dot(normalVector, lightVector), toonDiffuse;                   \n"+ // diffuse
                "       if(diffuse < 0.4) toonDiffuse = 0.0;                                            \n"+
                "       else if(diffuse < 0.41) toonDiffuse = smoothstep(0.5, 0.51, diffuse)*0.5;  \n"+
                "       else if(diffuse < 0.75) toonDiffuse = 0.5;                                      \n"+
                "       else if(diffuse < 0.76) toonDiffuse = 0.5+smoothstep(0.75, 0.76, diffuse)*0.5; \n"+
                "       else toonDiffuse = 1.0;                                                          \n"+

                //"       float specular = pow(max(dot(viewVector, reflectVector),0.0), 64.0);           \n"+ // specular
                "       float specular = pow(max(dot(normalVector, halfVector),0.0), 64.0);              \n"+
                "       float toonSpecular = smoothstep(0.1, 0.15, specular);                            \n"+

                "       float toonRim = pow(diffuse, 0.1) * (1.0 - dot(viewVector, normalVector));       \n"+ // rim
                "       toonRim = 0.1*smoothstep(0.92, 0.95, toonRim);                                       \n"+

                //"       float attenuation = 1.0/(1.0+0.09*distance+0.032*distance*distance);           \n"+
                "       Sum += toonDiffuse + toonSpecular + toonRim;                                          \n"+ // multiply attenuation if want to
                "   }                                                                 \n"+
                "   gl_FragColor = vec4(1.0, 1.0, 1.0, 1.0) * Sum * texture2D(u_Texture, v_TextureMap);    \n"+
                "}                                                                       \n";
        return fragmentShader;
    }

    protected static String getBackFragmentShader()
    {
        final String backFragmentShader =
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
                "       vec3 normalVector = normalize(v_Normal);                                         \n"+
                "       float distance = length(u_LightPos[i] - v_Position);                             \n"+

                "       float diffuse = dot(normalVector, lightVector);                   \n"+ // diffuse

                        //"       float specular = pow(max(dot(viewVector, reflectVector),0.0), 64.0);           \n"+ // specular
                "       float specular = pow(max(dot(normalVector, halfVector),0.0), 64.0);              \n"+

                "       Sum += diffuse + specular;                                          \n"+
                "   }                                                                 \n"+
                "   gl_FragColor = vec4(1.0, 0.6, 0.8, 1.0) * Sum * texture2D(u_Texture, v_TextureMap);    \n"+
                "}                                                                       \n";
        return backFragmentShader;
    }

}

