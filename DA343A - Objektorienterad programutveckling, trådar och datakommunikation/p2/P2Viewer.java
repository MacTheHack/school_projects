package p2;

import p1.*;

public class P2Viewer extends Viewer {
	public P2Viewer(MessageClient messageClient, int height, int width) {
		super(width, width);
		messageClient.addCallback(new CallbackClass());
	}

	private class CallbackClass implements CallbackInterface {
		public void update(Message msg) {
			setMessage(msg);
		}
	}
}
