package at.fhv.multisync.bl.sync.test;

import at.fhv.multisync.model.Job;

public class Test {
	public static void main(String[] args) {
		Job job = new Job("test");

		job.setMaster("c:\\source");
		job.addSlave("c:\\target");

		job.run();
	}
}
