package gu;

import java.util.HashMap;

public class ClientStorage {
	private HashMap<User,Client> clients = new HashMap<>();

	public synchronized void put(User user,Client client) {
		clients.put(user,client);
	}
	public synchronized Client get(User user) {
		return clients.get(user);
	}
}
