package com.sanoxy.repository;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class ResourceIdGenerator implements IdentifierGenerator {
	@Override
	public Serializable generate(SessionImplementor impl, Object object) throws HibernateException {
		return UUID.randomUUID().toString().replace("-", "");
	}
}