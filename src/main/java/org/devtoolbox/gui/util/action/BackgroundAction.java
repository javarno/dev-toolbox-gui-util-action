/*
 * MIT License
 *
 * Copyright © 2020-2023 dev-toolbox.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.devtoolbox.gui.util.action;

import org.devtoolbox.util.task.implementation.AsynchronousTask;
import org.devtoolbox.util.task.status.TaskStatus;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;


/**
 * @author Arnaud Lecollaire
 */
// TODO add javadoc, tests
public abstract class BackgroundAction extends AsynchronousTask {

    private ReadOnlyBooleanWrapper stoppedProperty = null;


    public BackgroundAction(final String name) {
        super(name);
    }

    @Override
    public void setStatus(final TaskStatus newStatus) {
        super.setStatus(newStatus);
        getStoppedProperty().set((newStatus == null) ? true : (newStatus == TaskStatus.STOPPED));
    }

	public boolean isStopped() {
        return getStoppedProperty().get();
    }

    public ReadOnlyBooleanProperty stoppedProperty() {
        return getStoppedProperty().getReadOnlyProperty();
    }

    protected ReadOnlyBooleanWrapper getStoppedProperty() {
        if (stoppedProperty == null) {
            stoppedProperty = new ReadOnlyBooleanWrapper(true);
        }
        return stoppedProperty;
    }

    @Override
    protected boolean isInResultThread() {
		return Platform.isFxApplicationThread();
	}

    @Override
    protected void executeInResultThread(Runnable runnable) {
		 Platform.runLater(runnable);
	}
}