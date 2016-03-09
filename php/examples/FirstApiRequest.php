<?php
/*
 * Copyright 2015 Google Inc.
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

/*
 * Sample application that authenticates and makes an API request.
 *
 */

/*
 * Provide path to client library.
 *
 * See README.md for details.
 */
require_once '/path/to/your-project/vendor/autoload.php';

session_start();

/*
 * You can retrieve this file from the Google Developers Console.
 *
 * See README.md for details.
 */
$key_file_location = '<PATH_TO_JSON>';

if ($key_file_location === '<PATH_TO_JSON>') {
    echo '<h1>WARNING: Authorization details not provided!</h1>';
    exit(1);
}

$client = new Google_Client();
$client->setApplicationName(
    'DoubleClick Ad Exchange Buyer REST API PHP Samples');

$service = new Google_Service_AdExchangeBuyer($client);

if (isset($_SESSION['service_token'])) {
    $client->setAccessToken($_SESSION['service_token']);
}

$client->setAuthConfig($key_file_location);
$client->addScope('https://www.googleapis.com/auth/adexchange.buyer');

if ($client->isAccessTokenExpired()) {
    $client->refreshTokenWithAssertion();
}

$_SESSION['service_token'] = $client->getAccessToken();

if ($client->getAccessToken()) {
    // Call the Accounts resource on the service to retrieve a list of
    // Accounts for the service account.
    $result = $service->accounts->listAccounts();

    print '<h2>Listing of user associated accounts</h2>';
    if (! isset($result['items']) || ! count($result['items'])) {
        print '<p>No accounts found</p>';
        return;
    } else {
        foreach ($result['items'] as $account) {
            printf('<pre>');
            print_r($account);
            printf('</pre>');
        }
    }
}
