<?php
/**
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
 * Authorizes with the ServiceAccount Authorization Flow and presents a menu of
 * DoubleClick Ad Exchange Buyer REST API samples to run.
 *
 */

/**
 * Provide path to client library.
 *
 * See README.md for details.
 */
require_once __DIR__ . '/vendor/autoload.php';
require_once 'htmlHelper.php';

session_start();

/**
 * You can retrieve this file from the Google Developers Console.
 *
 * See README.md for details.
 */
$keyFileLocation = '<PATH_TO_JSON>';

if ($keyFileLocation === '<PATH_TO_JSON>') {
  echo '<h1>WARNING: Authorization details not provided!</h1>';
  exit(1);
}

$client = new Google_Client();
$client->setApplicationName(
    'DoubleClick Ad Exchange Buyer REST API PHP Samples');

$service = new Google_Service_AdExchangeBuyer($client);
$serviceII = new Google_Service_AdExchangeBuyerII($client);

if (isset($_SESSION['service_token'])) {
  $client->setAccessToken($_SESSION['service_token']);
}

$client->setAuthConfig($keyFileLocation);
$client->addScope('https://www.googleapis.com/auth/adexchange.buyer');

if ($client->isAccessTokenExpired()) {
  $client->refreshTokenWithAssertion();
}

$_SESSION['service_token'] = $client->getAccessToken();

if ($client->getAccessToken()) {
  // Build the list of supported actions.
  $actions = getSupportedActions();

  // If the action is set dispatch the action if supported
  if (isset($_GET['action'])) {
    $action = $_GET['action'];

    if (in_array($action, $actions['v1'])) {
      require_once 'examples/v1_x/' . $action . '.php';
    } else if (in_array($action, $actions['v2'])) {
      require_once 'examples/v2_x/' . $action . '.php';
    } else {
      die("Unsupported action: " . $action . "\n");
    }

    // Render the required action.
    $class = ucfirst($action);
    $example = new $class();
    if ($example->getClientType() == ClientType::AdExchangeBuyer) {
      $example->setService($service);
    } else {
      $example->setService($serviceII);
    }
    printHtmlHeader($example->getName());
    try {
      $example->execute();
    } catch (ApiException $ex) {
      printf('An error as occurred while calling the example:<br/>');
      printf($ex->getMessage());
    }
    printSampleHtmlFooter();
  } else {
    // Show the list of links to supported actions.
    printHtmlHeader('Ad Exchange Buyer API PHP usage examples.');
    printExamplesIndex($actions);
    printHtmlFooter();
  }

  // The access token may have been updated.
  $_SESSION['service_token'] = $client->getAccessToken();
}

/**
 * Builds an array containing the supported actions.
 */
function getSupportedActions() {
  return [
      'v1' => [
          'GetAllAccounts',
          'GetCreative',
          'SubmitCreative',
          'UpdateAccount',
          'ListPerformanceReport',
          'InsertPretargetingConfig'
      ],
      'v2' => [
          'ListClientBuyers',
          'CreateClientBuyer',
          'UpdateClientBuyer',
          'ListInvitations',
          'CreateInvitation',
          'ListClientUsers',
          'UpdateClientUser',
          'CreateBidderLevelFilterSet',
          'ListBidderLevelFilterSets',
          'ListBidderLevelBidMetrics',
          'CreateAccountLevelFilterSet',
          'ListAccountLevelFilterSets',
          'ListAccountLevelBidMetrics'
    ]
  ];
}
