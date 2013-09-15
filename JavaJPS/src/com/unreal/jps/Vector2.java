/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unreal.jps;

/**
 *
 * @author Adrian
 */
public class Vector2 {

    public int x, y;

    public Vector2(int inX, int inY) {
        x = inX;
        y = inY;
    }
    
    public double getMagnitude()
    {
        return Math.sqrt(this.x^2 + this.y^2);
    }
}
