package com.example.opengl3d;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

public final class ObjLoader {

    public int numFaces;
    public float[] normalData;
    public float[] textureMapData;
    public float[] vertexData;
    private float scale = 0.01f;

    public ObjLoader(Context context, String file) {

        Vector<Float> vertices = new Vector<>();
        Vector<Float> normalData = new Vector<>();
        Vector<Float> textures = new Vector<>();
        Vector<String> faces = new Vector<>();

        BufferedReader reader = null;
        try {
            InputStreamReader in = new InputStreamReader(context.getAssets().open(file));
            reader = new BufferedReader(in);

            // read file until EOF
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("  | ");
                switch (parts[0]) {
                    case "v":
                        // vertices
                        vertices.add(Float.valueOf(parts[1])/scale);
                        vertices.add(Float.valueOf(parts[2])/scale);
                        vertices.add(Float.valueOf(parts[3])/scale);
                        break;
                    case "vt":
                        // textures
                        textures.add(Float.valueOf(parts[1]));
                        textures.add(Float.valueOf(parts[2]));
                        break;
                    case "vn":
                        // normalData
                        normalData.add(Float.valueOf(parts[1]));
                        normalData.add(Float.valueOf(parts[2]));
                        normalData.add(Float.valueOf(parts[3]));
                        break;
                    case "f":
                        // faces: vertex/texture/normal
                        faces.add(parts[1]);
                        faces.add(parts[2]);
                        faces.add(parts[3]);
                        if(parts.length==5) {
                            faces.add(parts[3]);
                            faces.add(parts[4]);
                            faces.add(parts[1]);
                        }break;
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        System.out.println(faces.size());
        numFaces = faces.size();
        this.normalData = new float[numFaces * 3];
        this.textureMapData = new float[numFaces * 2];
        this.vertexData = new float[numFaces * 3];
        int positionIndex = 0;
        int normalIndex = 0;
        int textureIndex = 0;
        for (String face : faces) {
            String[] parts = face.split("/");

            int index = 3 * (Short.valueOf(parts[0]) - 1);
            this.vertexData[positionIndex++] = vertices.get(index++);
            this.vertexData[positionIndex++] = vertices.get(index++);
            this.vertexData[positionIndex++] = vertices.get(index);

            index = 2 * (Short.valueOf(parts[1]) - 1);
            this.textureMapData[normalIndex++] = textures.get(index++); // NOTE: Bitmap gets y-inverted
            this.textureMapData[normalIndex++] = 1 - textures.get(index);

            index = 3 * (Short.valueOf(parts[2]) - 1);
            this.normalData[textureIndex++] = normalData.get(index++);
            this.normalData[textureIndex++] = normalData.get(index++);
            this.normalData[textureIndex++] = normalData.get(index);

        }
    }
}