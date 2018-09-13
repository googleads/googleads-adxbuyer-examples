/*
 * Copyright (c) 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.api.services.samples.adexchangebuyer.creativessubmit;

import java.io.IOException;

/**
 * User input fields specific to the Authorized Buyers Ad Exchange Buyer API.
 */
public class BuyerServiceInputUtils extends InputUtils {
  /**
   * the account id to work with; due to an issue with the client library you may need to cast this
   * to an int.
   */
  protected long getAccountId() throws IOException {
    return this.getLongInput("accountid", "Enter the account id", null);
  }

  /**
   * A buyer-specific ID identifying the creative.
   */
  protected String getCreativeId() throws IOException {
    return this.getStringInput("creativeId", "Enter the buyer creative id", null);
  }

  /**
   * When specified, only creatives having the given status are returned.
   */
  protected String getCreativeListFilter() throws IOException {
    return InputUtils.getStringInput(
        "Enter a filter [approved|disapproved|not_checked] or enter for none");
  }

  /**
   * Location for a .json file containing a serialized Creative object.
   */
  protected String getCreativeFilePath() throws IOException {
    return this.getStringInput("creativefile",
        "Enter a path for a json file", "./creative.json");
  }

  /**
   * The automatically generated PretargetingConfig identifier.
   */
  protected long getPretargetingConfigId() throws IOException {
    return this.getLongInput("pcid", "Enter the pretargeting config id", null);
  }

  /**
   * Location for a .json file containing a serialized PretargetingConfig object.
   */
  protected String getPretargetingFilePath() throws IOException {
    return this.getStringInput("pcfile",
        "Enter a path for a json file", "./pretargetingConfig.json");
  }

}
