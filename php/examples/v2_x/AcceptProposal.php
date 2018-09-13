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
 * This example illustrates how to accept the given proposal.
 */
class AcceptProposal extends BaseExample
{

  /**
   * @see BaseExample::getInputParameters()
   */
  protected function getInputParameters()
  {
    return [
        [
            'name' => 'account_id',
            'display' => 'Account ID',
            'required' => true
        ],
        [
            'name' => 'proposal_id',
            'display' => 'Proposal ID',
            'required' => true
        ],
        [
            'name' => 'proposal_revision',
            'display' => 'Proposal revision',
            'required' => true
        ]
    ];
  }

  /**
   * @see BaseExample::run()
   */
  public function run() {
    $values = $this->formValues;

    $accept_request =
        new Google_Service_AdExchangeBuyerII_AcceptProposalRequest();
    $accept_request->proposal_revision = $values['proposal_revision'];

    $proposal = $this->service->accounts_proposals->accept(
        $values['account_id'], $values['proposal_id'], $accept_request);

    print '<h2>Accepted Proposal</h2>';
    $this->printResult($proposal);
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
    return 'Marketplace: Accept Proposal';
  }
}

