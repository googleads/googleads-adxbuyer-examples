<?php
/**
 * Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

require_once __DIR__ . '/../../BaseExample.php';

/**
 * This example illustrates how to change the status of a client user; in this
 * case we are disabling it.
 */
class UpdateClientUser extends BaseExample {

  /**
   * @see BaseExample::getInputParameters()
   */
  protected function getInputParameters() {
    return [
        [
            'name' => 'accountId',
            'display' => 'Account id',
            'required' => true
        ],
        [
            'name' => 'clientAccountId',
            'display' => 'Client account ID',
            'required' => true
        ],
        [
            'name' => 'clientUserId',
            'display' => 'Client user ID',
            'required' => true
        ]
    ];
  }

  /**
   * @see BaseExample::run()
   */
  public function run() {
    $values = $this->formValues;
    $client = $this->service->accounts_clients_users->get(
        $values['accountId'], $values['clientAccountId'],
        $values['clientUserId']);
    $client->status = 'DISABLED';
    $result = $this->service->accounts_clients_users->update(
        $values['accountId'], $values['clientAccountId'],
        $values['clientUserId'], $client);
    $this->printResult($result);
  }

  /**
   * @see BaseExample::getClientType()
   */
  public function getClientType() {
    return ClientType::AdExchangeBuyerII;
  }

  /**
   * @see BaseExample::getName()
   */
  public function getName() {
    return 'Client Access: Update Client User';
  }
}

