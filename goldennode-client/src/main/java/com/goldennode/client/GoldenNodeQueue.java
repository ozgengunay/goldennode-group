package com.goldennode.client;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.UUID;

import com.goldennode.client.service.QueueService;
import com.goldennode.client.service.QueueServiceImpl;

public class GoldenNodeQueue<E> implements Queue<E> {
	QueueService<E> service;
	private String queueId;

	public GoldenNodeQueue() {
		this(UUID.randomUUID().toString());
	}

	public GoldenNodeQueue(String queueId) {
		this(queueId, new QueueServiceImpl<>());
	}

	public GoldenNodeQueue(String queueId, QueueService<E> service) {
		this.queueId = queueId;
		service = new QueueServiceImpl<>();
	}

	@Override
	public int size() {
		try {
			return service.size(queueId);
		} catch (GoldenNodeException e) {
			throw new GoldenNodeRuntimeException(e);
		}
	}

	@Override
	public boolean isEmpty() {
		try {
			return service.isEmpty(queueId);
		} catch (GoldenNodeException e) {
			throw new GoldenNodeRuntimeException(e);
		}
	}

	@Override
	public boolean contains(Object o) {
		try {
			return service.contains(queueId, o);
		} catch (GoldenNodeException e) {
			throw new GoldenNodeRuntimeException(e);
		}
	}

	@Override
	public Iterator<E> iterator() {
		try {
			return service.iterator(queueId);
		} catch (GoldenNodeException e) {
			throw new GoldenNodeRuntimeException(e);
		}
	}

	@Override
	public Object[] toArray() {
		try {
			return service.toArray(queueId);
		} catch (GoldenNodeException e) {
			throw new GoldenNodeRuntimeException(e);
		}
	}

	@Override
	public <T> T[] toArray(T[] a) {
		try {
			return service.toArray(queueId, a);
		} catch (GoldenNodeException e) {
			throw new GoldenNodeRuntimeException(e);
		}
	}

	@Override
	public boolean remove(Object o) {
		try {
			return service.remove(queueId, o);
		} catch (GoldenNodeException ex) {
			throw new GoldenNodeRuntimeException(ex);
		}
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		try {
			return service.containsAll(queueId, c);
		} catch (GoldenNodeException ex) {
			throw new GoldenNodeRuntimeException(ex);
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		try {
			return service.addAll(queueId, c);
		} catch (GoldenNodeException ex) {
			throw new GoldenNodeRuntimeException(ex);
		}
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		try {
			return service.removeAll(queueId, c);
		} catch (GoldenNodeException ex) {
			throw new GoldenNodeRuntimeException(ex);
		}
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		try {
			return service.retainAll(queueId, c);
		} catch (GoldenNodeException ex) {
			throw new GoldenNodeRuntimeException(ex);
		}
	}

	@Override
	public void clear() {
		try {
			service.clear(queueId);
		} catch (GoldenNodeException ex) {
			throw new GoldenNodeRuntimeException(ex);
		}
	}

	@Override
	public boolean add(E e) {
		try {
			return service.add(queueId, e);
		} catch (GoldenNodeException ex) {
			throw new GoldenNodeRuntimeException(ex);
		}
	}

	@Override
	public boolean offer(E e) {
		try {
			return service.offer(queueId, e);
		} catch (GoldenNodeException ex) {
			throw new GoldenNodeRuntimeException(ex);
		}
	}

	@Override
	public E remove() {
		try {
			return service.remove(queueId);
		} catch (GoldenNodeException ex) {
			throw new GoldenNodeRuntimeException(ex);
		}
	}

	@Override
	public E poll() {
		try {
			return service.poll(queueId);
		} catch (GoldenNodeException ex) {
			throw new GoldenNodeRuntimeException(ex);
		}
	}

	@Override
	public E element() {
		try {
			return service.element(queueId);
		} catch (GoldenNodeException ex) {
			throw new GoldenNodeRuntimeException(ex);
		}
	}

	@Override
	public E peek() {
		try {
			return service.peek(queueId);
		} catch (GoldenNodeException ex) {
			throw new GoldenNodeRuntimeException(ex);
		}
	}
}
