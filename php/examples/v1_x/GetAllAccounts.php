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
 * This example illustrates how to retrieve all accounts associated with the
 * user.
 */
class GetAllAccounts extends BaseExample {

  /**
   * @see BaseExample::run()
   */
  public function run() {
    $result = $this->service->accounts->listAccounts();

    print '<h2>Listing of user associated accounts</h2>';
    if (empty($result['items'])) {
      print '<p>No accounts found</p>';
      return;
    } else {
      foreach ($result['items'] as $account) {
        $this->printResult($account);
      }
    }
  }

  /**
   * @see BaseExample::getName()
   */
  public function getName() {
    return 'Get All Accounts';
  }
}

