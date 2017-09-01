package io.cisa.taxiiserver.domain;

import java.util.HashSet;
import java.util.Set;

import io.cisa.taxiiserver.domain.types.Client;
import io.cisa.taxiiserver.domain.types.Message;

public class Channel {
	private String id, name, description;
	private Client publisher;
	private Set<Client> subscribers = new HashSet<>();
	private Set<Message> messages = new HashSet<>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Client getPublisher() {
		return publisher;
	}
	public void setPublisher(Client publisher) {
		this.publisher = publisher;
	}
	public Set<Client> getSubscribers() {
		return subscribers;
	}
	public void setSubscribers(Set<Client> subscribers) {
		this.subscribers = subscribers;
	}
	public Set<Message> getMessages() {
		return messages;
	}
	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}
}
