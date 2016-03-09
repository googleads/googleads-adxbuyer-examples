<?php
/*
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

// Require the base class.
require_once __DIR__ . "/../BaseExample.php";

/**
 * This example illustrates how to create a new client buyer.
 */
class CreateClientBuyer extends BaseExample
{

    /**
     *
     * @see BaseExample::getInputParameters()
     */
    protected function getInputParameters()
    {
        return array(
            array(
                'name' => 'account_id',
                'display' => 'Account id',
                'required' => true
            ),
            array(
                'name' => 'client_name',
                'display' => 'Client Name',
                'required' => true
            ),
            array(
                'name' => 'client_entity_name',
                'display' => 'Client Entity Name',
                'required' => true
            ),
            array(
                'name' => 'client_entity_id',
                'display' => 'Client Entity id',
                'required' => true
            ),
            array(
                'name' => 'client_entity_type',
                'display' => 'Client Entity Type',
                'required' => true
            ),
            array(
                'name' => 'client_role',
                'display' => 'Client Role',
                'required' => true
            )
        );
    }

    /**
     *
     * @see BaseExample::run()
     */
    public function run()
    {
        $values = $this->formValues;
        print '<h2>Creating Client Buyer</h2>';

        $client = new Google_Service_AdExchangeBuyerII_Client();
        $client->clientName = $values['client_name'];
        $client->entityId = $values['client_entity_id'];
        $client->entityName = $values['client_entity_name'];
        $client->entityType = $values['client_entity_type'];
        $client->role = $values['client_role'];
        $client->visibleToSeller = false;

        $result = $this->service->accounts_clients->create(
            $values['account_id'], $client);
        $this->printResult($result);
    }

    /**
     *
     * @see BaseExample::getClientType()
     */
    function getClientType()
    {
        return ClientType::AdExchangeBuyerII;
    }

    /**
     *
     * @see BaseExample::getName()
     */
    public function getName()
    {
        return 'Client Access: Create Client Buyer';
    }
}

