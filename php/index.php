<?php
/*
 * Copyright 2014 Google Inc.
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

/*
 * Authorizes with the ServiceAccount Authorization Flow and presents a menu of
 * DoubleClick Ad Exchange Buyer REST API samples to run.
 *
 * @author David Torres <david.t@google.com>
 * @author Mark Saniscalchi <api.msaniscalchi@gmail.com>
 */

/*
 * Provide path to src directory of google-api-php-client.
 *
 * For example:"google-api-php-client/src/"
 */
set_include_path('<PATH_TO_PHP_CLIENT>' . PATH_SEPARATOR . get_include_path());

require_once 'Google/Client.php';
require_once 'Google/Service/AdExchangeBuyer.php';
require_once "htmlHelper.php";

session_start();

/*
 * You can retrieve these from the Google Developers Console.
 *
 * See README.md for details.
 */
$service_account_name = '<YOUR_SERVICE_ACCOUNT_EMAIL>';
$key_file_location = '<PATH_TO_P12>';

if ($service_account_name === '<YOUR_SERVICE_ACCOUNT_EMAIL>'
    || $key_file_location === '<PATH_TO_P12>') {
  echo '<h1>WARNING: Authorization details not provided!</h1>';
  exit(1);
}

$client = new Google_Client();
$client->setApplicationName('DoubleClick Ad Exchange Buyer REST API PHP' .
    ' Samples');

$service = new Google_Service_AdExchangeBuyer($client);

if (isset($_SESSION['service_token'])) {
  $client->setAccessToken($_SESSION['service_token']);
}
$key = file_get_contents($key_file_location);
$cred = new Google_Auth_AssertionCredentials(
    $service_account_name,
    array('https://www.googleapis.com/auth/adexchange.buyer'),
    $key
);

$client->setAssertionCredentials($cred);
if($client->getAuth()->isAccessTokenExpired()) {
  $client->getAuth()->refreshTokenWithAssertion($cred);
}

$_SESSION['service_token'] = $client->getAccessToken();

if ($client->getAccessToken()) {
  // Build the list of supported actions.
  $actions = getSupportedActions();

  // If the action is set dispatch the action if supported
  if (isset($_GET["action"])) {
    $action = $_GET["action"];
    if (!in_array($action, $actions)) {
      die('Unsupported action:' . $action . "\n");
    }
    // Render the required action.
    require_once 'examples/' . $action . '.php';
    $class = ucfirst($action);
    $example = new $class($service);
    printHtmlHeader($example->getName());
    try {
      $example->execute();
    } catch (apiException $ex) {
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
  return array('GetAllAccounts', 'GetCreative', 'GetDirectDeals',
      'SubmitCreative', 'UpdateAccount', 'ListPerformanceReport',
      'InsertPretargetingConfig');
}
