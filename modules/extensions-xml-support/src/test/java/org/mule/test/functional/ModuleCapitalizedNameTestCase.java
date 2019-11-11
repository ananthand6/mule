/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.test.functional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.mule.runtime.api.component.location.Location;
import org.mule.runtime.api.metadata.MetadataKeysContainer;
import org.mule.runtime.api.metadata.MetadataService;
import org.mule.runtime.api.metadata.resolving.MetadataResult;
import org.mule.runtime.core.api.event.CoreEvent;

import javax.inject.Inject;

import org.junit.Test;

public class ModuleCapitalizedNameTestCase extends AbstractXmlExtensionMuleArtifactFunctionalTestCase {

  @Inject
  private MetadataService metadataService;

  @Override
  protected String getModulePath() {
    return "modules/module-capitalized-name.xml";
  }

  @Override
  protected String getConfigFile() {
    return "flows/flows-using-module-capitalized-name.xml";
  }

  @Test
  public void testSetPayloadHardcoded() throws Exception {
    final CoreEvent muleEvent = flowRunner("testSetPayloadHardcodedFlow").run();
    assertThat(muleEvent.getMessage().getPayload().getValue(), is("hardcoded value"));
  }

  @Test
  public void testUnderscoreSetPayloadWithParameter() throws Exception {
    final CoreEvent muleEvent = flowRunner("testUnderscoreSetPayloadWithParameter").run();
    assertThat(muleEvent.getMessage().getPayload().getValue(), is("underscore property value, underscore parameter value"));
  }

  @Test
  public void testSetPayloadWithNameParameter() throws Exception {
    final CoreEvent muleEvent = flowRunner("testSetPayloadWithNameParameter").run();
    assertThat(muleEvent.getMessage().getPayload().getValue(), is("the name parameter"));
  }

  @Test
  public void testMetadata() {
    MetadataResult<MetadataKeysContainer> testSetPayloadWithNameParameter = metadataService.getMetadataKeys(Location.builder()
        .globalName("testSetPayloadWithNameParameter").addProcessorsPart().addIndexPart(0).build());
  }

}
