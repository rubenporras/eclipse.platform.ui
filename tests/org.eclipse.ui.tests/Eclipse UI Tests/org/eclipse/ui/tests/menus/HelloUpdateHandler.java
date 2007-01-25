/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/

package org.eclipse.ui.tests.menus;

import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.commands.ICallbackUpdater;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.menus.ICommandCallback;

/**
 * @since 3.3
 * 
 */
public class HelloUpdateHandler extends AbstractHandler implements
		ICallbackUpdater {

	private String myLabelState = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		if (event.getApplicationContext() instanceof IEvaluationContext) {
			IEvaluationContext app = (IEvaluationContext) event
					.getApplicationContext();
			IWorkbenchWindow window = (IWorkbenchWindow) app
					.getVariable(ISources.ACTIVE_WORKBENCH_WINDOW_NAME);
			if (window == null) {
				throw new ExecutionException("No active workbench window");
			}
			MessageDialog.openInformation(window.getShell(), "Hello",
					"Hello label update command!");
			myLabelState = "My New Item";
			ICommandService cs = (ICommandService) window
					.getService(ICommandService.class);
			cs.refreshCallbacks(event.getCommand().getId(), null);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.commands.ICallbackUpdater#updateCallback(org.eclipse.core.runtime.IAdaptable,
	 *      java.util.Map)
	 */
	public void updateCallback(IAdaptable callback, Map parameters) {
		if (myLabelState == null) {
			return;
		}
		ICommandCallback feedback = (ICommandCallback) callback
				.getAdapter(ICommandCallback.class);
		if (feedback != null) {
			feedback.setText(myLabelState);
		}
	}
}
