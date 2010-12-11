/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.ajocado.waiting.conditions;

import org.apache.commons.lang.Validate;
import org.jboss.ajocado.encapsulated.JavaScript;
import org.jboss.ajocado.framework.AjaxSelenium;
import org.jboss.ajocado.framework.AjaxSeleniumProxy;
import org.jboss.ajocado.locator.ElementLocator;
import org.jboss.ajocado.waiting.ajax.JavaScriptCondition;
import org.jboss.ajocado.waiting.selenium.SeleniumCondition;

import static org.jboss.ajocado.utils.text.SimplifiedFormat.format;
import static org.apache.commons.lang.StringEscapeUtils.escapeJavaScript;
import static org.jboss.ajocado.encapsulated.JavaScript.js;

/**
 * 
 * <p>
 * Implementation of Condition for waiting if element given by elementLocator has text equal to given text.
 * </p>
 * 
 * <p>
 * Implements Condition and JavaScriptCondition used in SeleniumWaiting and AjaxWaiting.
 * </p>
 * 
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TextEquals implements SeleniumCondition, JavaScriptCondition {

    /**
     * Proxy to local selenium instance
     */
    private AjaxSelenium selenium = AjaxSeleniumProxy.getInstance();
    
    /** The element locator. */
    private ElementLocator<?> elementLocator;

    /** The text. */
    private String text;

    /**
     * Instantiates a new text equals.
     */
    protected TextEquals() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.ajocado.waiting.Condition#isTrue()
     */
    public boolean isTrue() {
        Validate.notNull(elementLocator);
        Validate.notNull(text);

        return selenium.getText(elementLocator).equals(text);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.ajocado.waiting.ajax.JavaScriptCondition#getJavaScriptCondition()
     */
    public JavaScript getJavaScriptCondition() {
        String escapedLocator = escapeJavaScript(this.elementLocator.getAsString());
        String escapedText = escapeJavaScript(this.text);
        return js(format("selenium.isElementPresent('{0}') && (selenium.getText('{0}') == '{1}')", escapedLocator,
            escapedText));
    }

    /**
     * Factory method.
     * 
     * @return single instance of TextEquals
     */
    public static TextEquals getInstance() {
        return new TextEquals();
    }

    /**
     * <p>
     * Returns the TextEquals instance with given elementLocator set.
     * </p>
     * 
     * <p>
     * From this locator will be obtained the text.
     * </p>
     * 
     * @param elementLocator
     *            the element locator
     * @return the TextEquals object with preset locator
     */
    public TextEquals locator(ElementLocator<?> elementLocator) {
        Validate.notNull(elementLocator);

        TextEquals copy = copy();
        copy.elementLocator = elementLocator;

        return copy;
    }

    /**
     * <p>
     * Returns the TextEquals instance with text set.
     * </p>
     * 
     * <p>
     * For equality with this text the condition will wait.
     * </p>
     * 
     * @param text
     *            it should wait for equality
     * @return the TextEquals object with preset text
     */
    public TextEquals text(String text) {
        Validate.notNull(text);

        TextEquals copy = copy();
        copy.text = text;

        return copy;
    }

    /**
     * Returns the exact copy of this ElementPresent object.
     * 
     * @return the copy of this TextEquals object
     */
    private TextEquals copy() {
        TextEquals copy = new TextEquals();
        copy.elementLocator = elementLocator;
        copy.text = text;
        return copy;
    }
}