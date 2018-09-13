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
 * This example illustrates how to retrieve proposals for a given filter.
 */
class ListProposals extends BaseExample
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
        ],
        [
            'name' => 'filter',
            'display' => 'filter',
            'required' => false
        ],
        [
            'name' => 'filter_syntax',
            'display' => 'filter syntax',
            'required' => false
        ]
    ];
  }

  /**
   * @see BaseExample::run()
   */
  public function run() {
    $values = $this->formValues;

    $filterSyntax = $values['filter_syntax'];
    if (is_null($filterSyntax)) {
        $filterSyntax = 'LIST_FILTER';
    }

    $result = $this->service->accounts_proposals->listAccountsProposals(
        $values['account_id'],
        [
            'filter' => $values['filter'],
            'filterSyntax' => $filterSyntax
        ]
    );
    print '<h2>Listing Proposals</h2>';
    if (!isset($result['proposals']) || !count($result['proposals'])) {
      print '<p>No proposals found</p>';
      return;
    } else {
      foreach ($result['proposals'] as $proposal) {
        $this->printResult($proposal);
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
    return 'Marketplace: List Proposals';
  }
}

