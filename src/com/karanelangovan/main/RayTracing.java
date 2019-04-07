package com.karanelangovan.main;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;

import javax.swing.JFrame;

import com.karanelangovan.camera.Camera;
import com.karanelangovan.objects.Light;
import com.karanelangovan.objects.Sphere;
import com.karanelangovan.util.Matrix4;
import com.karanelangovan.util.Ray;
import com.karanelangovan.util.Vector3;

public class RayTracing extends Canvas implements Runnable {
	private static final long serialVersionUID = 3678851152445158926L;

	private JFrame frame;
	private boolean running = false;
	
	private long tickCount = 0;
	
	public static final int WIDTH = 1000, HEIGHT = 700;
	public static final String NAME = "Ray Tracing";
	
	private LinkedList<Sphere> spheres;
	private Light light;
	private Camera camera;
	
	private Matrix4 rotation = Matrix4.getTranslationMatrix(0, 0, -100).compose(Matrix4.getYRotationMatrix(0.01)).compose(Matrix4.getTranslationMatrix(0, 0, 100));
	
	public RayTracing() {
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setMaximumSize(new Dimension(WIDTH, HEIGHT));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		frame = new JFrame(NAME);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		double X = 60;
		
		spheres = new LinkedList<Sphere>();
		spheres.add(new Sphere(new Vector3(0, 0, 50), 30, new Color(27, 211, 156)));
		spheres.add(new Sphere(new Vector3(X, -X, 250), 53, new Color(12, 225, 107)));
		spheres.add(new Sphere(new Vector3(-X, -X, 200), 45, new Color(70, 222, 242)));
		spheres.add(new Sphere(new Vector3(X, X, 400), 55, new Color(51, 133, 173)));
		spheres.add(new Sphere(new Vector3(-X, X, 400), 60, new Color(218, 249, 14)));
		
		light = new Light(new Vector3(0, 10, 100));
		
		camera = new Camera(new Vector3(0, 0, -100), 600, WIDTH, HEIGHT);
	}

	public static void main(String[] args) {
		new RayTracing().start();
	}
	
	public synchronized void start() {
		running = true;
		new Thread(this).start();
	}
	
	public synchronized void stop() {
		running = false;
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / 60D;
		
		int ticks = 0;
		
		long lastTimer = System.currentTimeMillis();
		double delta = 0;

		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			
			lastTime = now;
			
			boolean shouldRender = false;

			while(delta >= 1) {
				ticks++;
				tickCount++;
				tick();
				delta--;
				shouldRender = true;
			}

			if(shouldRender) {
				render();
			}
			
			if(System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;

				System.out.println("FPS: " + ticks);

				ticks = 0;
			}
		}
	}
	
	private void tick() {
		if(tickCount % 3 == 0) {
			spheres.getFirst().setPosition(rotation.transform(spheres.getFirst().getPosition()));
		}
	}
	
	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(2);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		camera.render(g, spheres, light);
		
		g.dispose();
		
		bs.show();
	}

	static void testRaySphereIntersect() {
		Ray ray = new Ray(new Vector3(), new Vector3(1, 0, 0));
		Sphere sphere = new Sphere(new Vector3(5.6, 0, 1.6), 2.8);
		
		Vector3 intersect = ray.getIntersectWith(sphere);
		
		//should be about (3.3, 0, 0)
		System.out.println(intersect);
	}

	static void testRayTrace() {
		LinkedList<Sphere> spheres = new LinkedList<Sphere>();
		spheres.add(new Sphere(new Vector3(0, 0, 60), 50));
		
		Light light = new Light(new Vector3(0, 10, 0));
		
		Camera camera = new Camera(new Vector3(0, 0, -100), 100, WIDTH, HEIGHT);
		
		Ray ray = new Ray(camera.getPosition(), new Vector3(0, 0, 1));
		
		System.out.println(camera.rayTrace(ray, spheres, light));
	}
}
