<?php
/**
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

require_once __DIR__ . '/../../BaseExample.php';

/**
 * This example illustrates how to retrieve publisher profiles.
 */
class ListPublisherProfiles extends BaseExample
{

  /**
   * @see BaseExample::getInputParameters()
   */
  protected function getInputParameters() {
    return [
        [
            'name' => 'account_id',
            'display' => 'Account ID',
            'required' => true
        ]
    ];
  }

  /**
   * @see BaseExample::run()
   */
  public function run() {
    $values = $this->formValues;

    $result = $this->service->accounts_publisherProfiles
        ->listAccountsPublisherProfiles(
            $values['account_id']);
    print '<h2>Listing Publisher Profiles</h2>';
    if (!isset($result['publisherProfiles']) ||
        !count($result['publisherProfiles'])) {
      print '<p>No publisher profiles found</p>';
      return;
    } else {
      foreach ($result['publisherProfiles'] as $publisherProfiles) {
        $this->printResult($publisherProfiles);
      }
    }
  }

  /*
   * @see BaseExample::getClientType()
   */
  function getClientType() {
    return ClientType::AdExchangeBuyerII;
  }

  /**
   * @see BaseExample::getName()
   */
  public function getName() {
    return 'Marketplace: List Publisher Profiles';
  }
}

