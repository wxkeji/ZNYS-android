package com.znys.model.entity;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

/**
 * Created by mushroom on 2015/6/16.
 */
public class Cube {

    private IntBuffer mVertexBuffer;

    public Cube() {
        int one = 0x10000;
        int vertices[] = {

                // FRONT
                -one, -one, one, one, -one, one,
                -one, one, one, one, one, one,
                // BACK
                -one, -one, -one, -one, one, -one,
                one, -one, -one, one, one, -one,
                // LEFT
                -one, -one, one, -one, one, one,
                -one, -one, -one, -one, one, -one,
                // RIGHT
                one, -one, -one, one, one, -one,
                one, -one, one, one, one, one,
                // TOP
                -one, one, one, one, one, one,
                -one, one, -one, one, one, -one,
                // BOTTOM
                -one, -one, one, -one, -one, -one,
                one, -one, one, one, -one, -one,


        };

        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        mVertexBuffer = vbb.asIntBuffer();
        mVertexBuffer.put(vertices);
        mVertexBuffer.position(0);
    }

    public IntBuffer getBuffer() {
        return this.mVertexBuffer;
    }
}

