/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jface.text;


/**
 * A position updater is responsible for adapting document positions. When
 * installed on a document, the position updater updates the document's
 * positions to changes applied to this document. Document updaters can be
 * selective, i.e. they might only update positions of a certain category.
 * <p>
 * Position updaters are of primary importance for the definition of the
 * semantics of positions.
 * <p>
 * Clients may implement this interface or use the standard implementation
 * {@link org.eclipse.jface.text.DefaultPositionUpdater}.
 * </p>
 *
 * @see org.eclipse.jface.text.IDocument
 * @see org.eclipse.jface.text.Position
 */
public interface IPositionUpdater {

	/**
	 * Adapts positions to the change specified by the document event.
	 * It is ensured that the document's partitioning has been adapted to
	 * this document change and that all the position updaters which have
	 * a smaller index in the document's position updater list have been called.
	 *
	 * @param event the document event describing the document change
	 */
	void update(DocumentEvent event);
}
