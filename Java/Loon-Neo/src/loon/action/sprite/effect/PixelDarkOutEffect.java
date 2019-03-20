/**
 * Copyright 2008 - 2015 The Loon Game Engine Authors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * @project loon
 * @author cping
 * @email：javachenpeng@yahoo.com
 * @version 0.5
 */
package loon.action.sprite.effect;

import loon.LSystem;
import loon.canvas.LColor;
import loon.opengl.GLEx;

public class PixelDarkOutEffect extends PixelBaseEffect {

	private float t_x;

	private float t_y;

	private TriangleEffect[] de;
	
	public PixelDarkOutEffect(LColor color) {
		this(color,0, 0, LSystem.viewSize.getWidth() / 2, LSystem.viewSize
				.getHeight() / 2);
	}

	public PixelDarkOutEffect(LColor color, float x, float y,float w,float h) {
		super(color, x, y, w, h);
		this.t_x = x;
		this.t_y = y;
		float[][] res1 = { { 0.0f, 30f }, { 24f, -15f }, { -24f, -15f } };
		float[][] res2 = { { 24f, 15f }, { -24f, 15f }, { 0.0f, -30f } };
		this.de = new TriangleEffect[4];
		this.de[0] = new TriangleEffect(w,h,res1, 0, 0, -9f);
		this.de[1] = new TriangleEffect(w,h,res2,0, 0, -9f);
		this.de[2] = new TriangleEffect(w,h,res1, 0,0, -9f);
		this.de[3] = new TriangleEffect(w,h,res2, 0, 0, -9f);
		this.limit = 90;
		
		triangleEffects.add(de);
		setDelay(0);
		setEffectDelay(0);
	}

	@Override
	public void draw(GLEx g, float tx, float ty) {
		if (super.completed) {
			return;
		}
		float x = t_x - tx;
		float y = t_y - ty;
		int tmp = g.color();
		g.setColor(_baseColor);
		if (super.frame == 40) {
			de[0].setMoveX(5f);
			de[1].setMoveX(-5f);
			de[2].setMoveY(5f);
			de[3].setMoveY(-5f);
		}
		for (int j = 0; j < de.length; j++) {
			de[j].drawPaint(g, x, y);
		}
		if (super.frame >= limit) {
			this.completed = true;
		}
		g.setColor(tmp);
	}

}
