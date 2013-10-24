package cn.jhc.opengldemo;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class GLView extends GLSurfaceView {
	
	private final GLRenderer renderer;

	public GLView(Context context) {
		super(context);
		renderer = new GLRenderer(context);
		setDebugFlags(DEBUG_CHECK_GL_ERROR | DEBUG_LOG_GL_CALLS);
		setRenderer(renderer);
	}

}
