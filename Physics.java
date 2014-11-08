
public abstract class Physics {
	
	private static final double AIR_RESISTANCE = .5;
	protected static final Vector g = new Vector(0, .5);
	
	public static void apply(Movable m){
		m.getA().add(g);
//		System.out.print(m.getA().getCY() + " ");
		if(m.getSouthC()){
			m.getA().add(normalForce());
//			System.out.print(m.getA().getCY() + " ");
		}
		if(m.shouldApplyMoveAccel()){
			applyAccel(m);
		}
		applyFriction(m);
//		System.out.println(m.getA().getCX() + " " + m.getA().getCY());
	}
	
	public static void applyAccel(Movable m) {// (C1 - v)^2/C1*C2
//		double accel = (m.getStandardStep() - m.getRunningdir() * m.getV().getCX()) * m.getAccelSpeed();
		double accel = m.getRunningdir() * (m.getStandardStep()) * m.getAccelSpeed();
//		System.out.print(accel + " ");
		m.getA().add(new Vector(accel, 0));
	}
	
	public static void applyFriction(Movable m) {
		double friction = ((- Math.signum(m.getV().getCX())) * Math.abs(m.getV().getCX())) * m.getAccelSpeed();
//		System.out.println(friction);
		double scale = 1;
//		System.out.println(((double) m.getRunningdir()) * friction + " " + m.getRunningdir() + " " + friction);
//		System.out.println(m.getV().getCX() * friction);
		if(m.getV().getCX() * m.getRunningdir() < 0){
			System.out.println("e");
			scale = 3;
		}
		m.getA().add(new Vector(scale * friction, 0));
	}
	
	public static Vector normalForce(){
		return g.scale(-1);
	}
	
	
}
