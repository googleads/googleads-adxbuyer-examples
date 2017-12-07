<?php
/**
 * Copyright 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

require_once __DIR__ . '/../../BaseExample.php';

/**
 * This sample illustrates how to do a sparse update of some of the account
 * attributes.
 */
class UpdateAccount extends BaseExample {

  /**
   * @see BaseExample::getInputParameters()
   */
  protected function getInputParameters() {
    return [
        [
            'name' => 'accountId',
            'display' => 'Account ID to update',
            'required' => true
        ],
        [
            'name' => 'cookieMatchingUrl',
            'display' => 'New cookie matching URL',
            'required' => true
        ]
    ];
  }

  /**
   * @see BaseExample::run()
   */
  public function run() {
    $values = $this->formValues;
    $account = new Google_Service_AdExchangeBuyer_Account();
    $account->id = $values['accountId'];
    $account->cookieMatchingUrl = $values['cookieMatchingUrl'];

    // Note: Unlike update, patch allows you to specify only the fields you
    // want to update.
    $account = $this->service->accounts->patch($values['accountId'], $account);
    print '<h2>Submitted account</h2>';
    $this->printResult($account);
  }

  /**
   * @see BaseExample::getName()
   */
  public function getName() {
    return 'Update Account';
  }
}

