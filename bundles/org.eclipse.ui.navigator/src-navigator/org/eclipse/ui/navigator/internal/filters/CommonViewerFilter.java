/*******************************************************************************
 * Copyright (c) 2003, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.navigator.internal.filters;

import java.util.List;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.navigator.INavigatorExtensionFilter;
import org.eclipse.ui.navigator.NavigatorContentService;
import org.eclipse.ui.navigator.internal.extensions.NavigatorActivationService;
import org.eclipse.ui.navigator.internal.extensions.NavigatorContentDescriptor;
import org.eclipse.ui.navigator.internal.extensions.NavigatorContentDescriptorRegistry;

/**
 * <p>
 * The following class is experimental until fully documented.
 * </p>
 */
public class CommonViewerFilter extends ViewerFilter {

	private static final NavigatorActivationService NAVIGATOR_ACTIVATION_SERVICE = NavigatorActivationService.getInstance();
	private static final NavigatorContentDescriptorRegistry CONTENT_DESCRIPTOR_REGISTRY = NavigatorContentDescriptorRegistry.getInstance();
	private final CommonViewer commonViewer;
	private final NavigatorContentService contentService;

	/**
	 *  
	 */
	public CommonViewerFilter(CommonViewer aViewer) {
		super();
		commonViewer = aViewer;
		contentService = aViewer.getNavigatorContentService();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
	 *      java.lang.Object, java.lang.Object)
	 */
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		boolean select = true;
		List contentDescriptors = CONTENT_DESCRIPTOR_REGISTRY.getEnabledContentDescriptors(element);

		for (int extindex = 0; extindex < contentDescriptors.size() && select; extindex++) {
			NavigatorContentDescriptor descriptor = (NavigatorContentDescriptor) contentDescriptors.get(extindex);
			if (NAVIGATOR_ACTIVATION_SERVICE.isNavigatorExtensionActive(contentService.getViewerId(), descriptor.getId())) {

				ExtensionFilterDescriptor[] enabledFilters = ExtensionFilterRegistryManager.getInstance().getViewerRegistry(contentService.getViewerId()).getActiveDescriptors(descriptor.getId());

				for (int filterindx = 0; filterindx < enabledFilters.length; filterindx++) {
					INavigatorExtensionFilter filter = enabledFilters[filterindx].getInstance();
					/*
					 * System.out.println("Element: " + element + " isFiltered: " +
					 * !filter.select(getExtensionSite(), parentElement, element));
					 */
					if (!filter.select(commonViewer, parentElement, element)) {
						select = false;
						break;
					}
				}
			}
		}

		return select;
	}

}