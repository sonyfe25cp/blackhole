package us.codecraft.blackhole.connector;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPConnectionResponser {

	private static final Logger logger = Logger
			.getLogger(UDPConnectionResponser.class);

	private final DatagramSocket socket;
	private final DatagramPacket inDataPacket;

	public UDPConnectionResponser(DatagramSocket socket,
			DatagramPacket inDataPacket) {
		super();
		this.socket = socket;
		this.inDataPacket = inDataPacket;
	}

	public DatagramPacket getInDataPacket() {
		return inDataPacket;
	}

	public void response(byte[] response) {

		try {
			logger.debug("u1");
			if (response == null) {
				logger.error("no answer is coming here?");
				return;
			}
			logger.debug("u2");
			DatagramPacket outdp = new DatagramPacket(response,
					response.length, inDataPacket.getAddress(),
					inDataPacket.getPort());

			InetAddress address = inDataPacket.getAddress();
			if(address!= null){
				logger.debug(address.toString());
			}else{
				logger.debug("u2.5 null");
			}
			outdp.setData(response);
			outdp.setLength(response.length);
			outdp.setAddress(inDataPacket.getAddress());
			outdp.setPort(inDataPacket.getPort());
			

			try {
				socket.send(outdp);
				logger.debug("u3");
			} catch (IOException e) {

				logger.debug("Error sending UDP response to "
						+ inDataPacket.getAddress() + ", " + e);
				logger.error("u4");
			}

		} catch (Throwable e) {
			logger.debug("u5");
			logger.warn(
					"Error processing UDP connection from "
							+ inDataPacket.getSocketAddress() + ", ", e);
		}
	}
}
