/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.jms;

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;

import javax.jms.JMSException;
import javax.jms.Message;

public abstract class AbstractRedeliveryHandler implements RedeliveryHandler
{
    protected JmsConnector connector;

    public abstract void handleRedelivery(Message message) throws JMSException, MuleException;

    /**
     * The connector associated with this handler is set before
     * <code>handleRedelivery()</code> is called
     * 
     * @param connector the connector associated with this handler
     */
    public void setConnector(JmsConnector connector)
    {
        this.connector = connector;
    }
    
    protected MuleMessage createMuleMessage(Message message)
    {
        try
        {
            String encoding = connector.getMuleContext().getConfiguration().getDefaultEncoding();
            return connector.createMuleMessageFactory().create(message, encoding);
        }
        catch (Exception e)
        {
            return new DefaultMuleMessage(message, connector.getMuleContext());
        }
    }
}
