package com.vanyle.math;

import com.vanyle.misc.Tools;

public class Matrix {
	
	private double[][] matrix;
	
	public Matrix(double[][] matrix){
		this.matrix = matrix;
	}
	public Vector2d apply(Vector2d v){
		return v.matrix(matrix);
	}
	public Vector3d apply(Vector3d v){
		return v.matrix(matrix);
	}
	@Override
	public String toString(){
		return Tools.dispArray(matrix);
	}
}
