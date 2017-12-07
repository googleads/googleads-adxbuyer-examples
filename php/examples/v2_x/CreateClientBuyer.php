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
 * This example illustrates how to create a new client buyer.
 */
class CreateClientBuyer extends BaseExample
{

  /**
   * @see BaseExample::getInputParameters()
   */
  protected function getInputParameters() {
    return [
        [
            'name' => 'accountId',
            'display' => 'Account ID',
            'required' => true
        ],
        [
            'name' => 'clientName',
            'display' => 'Client name',
            'required' => true
        ],
        [
            'name' => 'clientEntityName',
            'display' => 'Client entity name',
            'required' => true
        ],
        [
            'name' => 'clientEntityId',
            'display' => 'Client entity ID',
            'required' => true
        ],
        [
            'name' => 'clientEntityType',
            'display' => 'Client entity type',
            'required' => true
        ],
        [
            'name' => 'clientRole',
            'display' => 'Client role',
            'required' => true
        ]
    ];
  }

  /**
   * @see BaseExample::run()
   */
  public function run() {
    $values = $this->formValues;
    print '<h2>Creating Client Buyer</h2>';

    $client = new Google_Service_AdExchangeBuyerII_Client();
    $client->clientName = $values['clientName'];
    $client->entityId = $values['clientEntityId'];
    $client->entityName = $values['clientEntityName'];
    $client->entityType = $values['clientEntityType'];
    $client->role = $values['clientRole'];
    $client->visibleToSeller = false;

    $result = $this->service->accounts_clients->create(
        $values['accountId'], $client);
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
    return 'Client Access: Create Client Buyer';
  }
}

