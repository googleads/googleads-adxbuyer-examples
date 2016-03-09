<?php
/*
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

/**
 * Include HTML generation functions and Client Type
 */
 require_once "htmlHelper.php";
 require_once "ClientType.php";

/**
 * Base class for all examples, contains helper methods to support examples
 * input and rendering results.
 */
abstract class BaseExample {
  protected $service;

  /**
   * Inject the dependency.
   * @param Google_Service $service an authenticated
   *     instance of Google_Service.
   */
  public function setService(Google_Service $service) {
    $this->service = $service;
  }

  /**
   * Contains the logic of the example.
   */
  abstract protected function run();

  /**
   * Executes the example, checks if the example requires parameters and
   * request them before invoking run.
   */
  public function execute() {
    if (count($this->getInputParameters())) {
      if ($this->isSubmitComplete()) {
        $this->formValues = $this->getFormValues();
        $this->run();
      } else {
        $this->renderInputForm();
      }
    } else {
      $this->run();
    }
  }

  /**
   * Gives a display name of the example.
   * To be implemented in the specific example class.
   */
  abstract public function getName();

  /**
   * Return the ClientType, indicating which endpoint the example should use.
   * @return ClientType 
   */
  public function getClientType() {
    return ClientType::AdExchangeBuyer;
  }

  /**
   * Returns the list of input parameters of the example.
   * To be overriden by examples that require parameters.
   * @return array
   */
  protected function getInputParameters() {
    return array();
  }

  /**
   * Renders an input form to capture the example parameters.
   */
  protected function renderInputForm() {
    $parameters = $this->getInputParameters();
    if (count($parameters)) {
      printf('<h2>Enter %s parameters</h2>', $this->getName());
      print '<form method="POST"><fieldset>';
      foreach ($parameters as $parameter) {
        $name = $parameter['name'];
        $display = $parameter['display'];
        $currentValue = isset($_POST[$name]) ? $_POST[$name] : '';
        printf('%s: <input name="%s" value="%s">', $display, $name,
            $currentValue);
        if ($parameter['required']) {
          print '*';
        }
        print '</br>';
      }
      print '</fieldset>*required<br/>';
      print '<input type="submit" name="submit" value="Submit"/>';
      print '</form>';
    }
  }

  /**
   * Checks if the form has been submitted and all required parameters are
   * set.
   * @return bool
   */
  protected function isSubmitComplete() {
    if (!isset($_POST['submit'])) {
      return false;
    }
    foreach ($this->getInputParameters() as $parameter) {
      if ($parameter['required'] &&
          empty($_POST[$parameter['name']])) {
        return false;
      }
    }
    return true;
  }

  /**
   * Retrieves the submitted form values.
   * @return array
   */
  protected function getFormValues() {
    $input = array();
    foreach ($this->getInputParameters() as $parameter) {
      if (isset($_POST[$parameter['name']])) {
        $input[$parameter['name']] = $_POST[$parameter['name']];
      }
    }
    return $input;
  }

  /**
   * Prints out the given result object.
   * @param array $result
   */
  protected function printResult($result) {
    printf('<pre>');
    print_r($result);
    printf('</pre>');
  }
}

