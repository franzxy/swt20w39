/*
 * Copyright 2013-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pharmacy.catalog;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import pharmacy.AbstractIntegrationTests;

/**
 * Integration tests interacting with the {@link CatalogController} directly.
 * 
 * @author Oliver Gierke
 */
class CatalogControllerIntegrationTests extends AbstractIntegrationTests {


	@Autowired CatalogController controller;

	/**
	 * Integration test for an individual controller.
	 */

	@Test
	@SuppressWarnings("unchecked")
	public void controllerIntegrationTest() {

		Model model = new ExtendedModelMap();

		String returnedView = controller.catalog("", "", false, model);

		assertThat(returnedView).isEqualTo("index");

		//Iterable<Object> object = (Iterable<Object>) model.asMap().get("index");

		//assertThat(object).hasSize(32);
	}

}
