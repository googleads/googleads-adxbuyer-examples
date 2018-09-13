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

/**
 * Menu driven Authorized Buyers Ad Exchange Buyer API Commands
 * With these commands you can handle a simple new setup for real-time bidding;
 * configuring pre-targeting and submitting/resubmitting creatives.
 * For details look for the guide on the the Ad Exchange Buyer API site
 * https://developers.google.com/authorized-buyers/apis
 */
public class Selection {

  /**
   * Interface that allows you to run code on a BuyerServiceHelper
   */
  public interface ServiceMethod {
    void run(BuyerServiceHelper sh);
  }

  /**
   * enum of menu options and their associated methods
   */
  public enum MenuOptions implements ServiceMethod {
    AL { @Override public void run(BuyerServiceHelper sh) {sh.accountsList(); } },
    CL { @Override public void run(BuyerServiceHelper sh) {sh.creativesList(); } },
    CG { @Override public void run(BuyerServiceHelper sh) {sh.creativesGet(); } },
    CI { @Override public void run(BuyerServiceHelper sh) {sh.creativesInsert(); } },
    PL { @Override public void run(BuyerServiceHelper sh) {sh.pretargetingConfigList(); } },
    PG { @Override public void run(BuyerServiceHelper sh) {sh.pretargetingConfigGet(); } },
    PI { @Override public void run(BuyerServiceHelper sh) {sh.pretargetingConfigInsert(); } },
    PU { @Override public void run(BuyerServiceHelper sh) {sh.pretargetingConfigUpdate(); } }
  }

  /**
   * Main loop for the example
   * Read for the input buffer, running commands until an X is entered
   */
  public static void main(String[] args) throws Exception {
    BuyerServiceInputUtils inputHelper = new BuyerServiceInputUtils();
    BuyerServiceHelper serviceHelper = new BuyerServiceHelper(inputHelper);

    String command = InputUtils.getStringInput("Enter the two captial letter code or ? for help");
    while (!command.equalsIgnoreCase("X")) {
      try {
        MenuOptions y = MenuOptions.valueOf(command.toUpperCase());
        y.run(serviceHelper);
      } catch (java.lang.IllegalArgumentException e) {
        System.out.println("Account (List)");
        System.out.println("Creatives (List|Get|Insert)");
        System.out.println("Pretargetingconfig (List|Get|Insert|Update)");
        System.out.println("X to eXit");
        System.out.println("e.g. PL will List Pretargeting");
      }
      command = InputUtils.getStringInput("Enter the two captial letter code or ? for help");
    }

    System.out.println("Done");
    return;
  }
}