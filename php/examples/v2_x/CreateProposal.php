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
 * This example illustrates how to create a proposal for a
 * Programmatic Guaranteed Deal.
 */
class CreateProposal extends BaseExample
{

  /**
   * @see BaseExample::getInputParameters()
   */
  protected function getInputParameters() {
    return [
        [
            'name' => 'buyer_account_id',
            'display' => 'Buyer account ID',
            'required' => true
        ],
        [
            'name' => 'seller_account_id',
            'display' => 'Seller account ID',
            'required' => true
        ],
        [
            'name' => 'seller_sub_account_id',
            'display' => 'Seller sub-account ID',
            'required' => false
        ]
    ];
  }

  /**
   * @see BaseExample::run()
   */
  public function run() {
    $values = $this->formValues;

    // Build the proposal for the proposals.create request.
    $buyer = new Google_Service_AdExchangeBuyerII_Buyer();
    $buyer->accountId = $values['buyer_account_id'];

    $seller = new Google_Service_AdExchangeBuyerII_Seller();
    $seller->accountId = $values['seller_account_id'];

    if (isset($values['seller_sub_account_id'])) {
      $seller->subAccountId = $values['seller_sub_account_id'];
    }

    $proposal = new Google_Service_AdExchangeBuyerII_Proposal();
    $proposal->buyer = $buyer;
    $proposal->seller = $seller;
    $proposal->displayName = 'Test Proposal #' . rand();

    // Build the Programmatic Guaranteed Deal to be associated with the
    // proposal.
    $egsAmount = new Google_Service_AdExchangeBuyerII_Money();
    $egsAmount->currencyCode = 'USD';
    $egsAmount->units = '0';
    $egsAmount->nanos = '1';

    $estimatedGrossSpend = new Google_Service_AdExchangeBuyerII_Price();
    $estimatedGrossSpend->pricingType = 'COST_PER_MILLE';
    $estimatedGrossSpend->amount = $egsAmount;

    $fixedPriceAmount = new Google_Service_AdExchangeBuyerII_Money();
    $fixedPriceAmount->currencyCode = 'USD';
    $fixedPriceAmount->units = '0';
    $fixedPriceAmount->nanos = '1';

    $fixedPrice = new Google_Service_AdExchangeBuyerII_Price();
    $fixedPrice->pricingType = 'COST_PER_MILLE';
    $fixedPrice->amount = $fixedPriceAmount;

    $price = new Google_Service_AdExchangeBuyerII_PricePerBuyer();
    $price->price = $fixedPrice;
    $price->buyer = $buyer;

    // The pricing terms used in this example are guaranteed fixed price terms,
    // making this a programmatic guaranteed deal. Alternatively, you could use
    // non-guaranteed fixed price terms to specify a preferred deal. Private
    // auction deals use non-guaranteed auction terms; however, only sellers
    // can create this deal type.
    $guaranteedFixedPriceTerms =
        new Google_Service_AdExchangeBuyerII_GuaranteedFixedPriceTerms();
    $guaranteedFixedPriceTerms->guaranteedLooks = '1';
    $guaranteedFixedPriceTerms->guaranteedImpressions = '1';
    $guaranteedFixedPriceTerms->fixedPrices = [$price];
    $guaranteedFixedPriceTerms->minimumDailyLooks = '1';

    $dealTerms = new Google_Service_AdExchangeBuyerII_DealTerms();
    $dealTerms->description = 'Test deal.';
    $dealTerms->brandingType = 'BRANDED';
    $dealTerms->sellerTimeZone = 'America/New_York';
    $dealTerms->estimatedGrossSpend = $estimatedGrossSpend;
    $dealTerms->estimatedImpressionsPerDay = '1';
    $dealTerms->guaranteedFixedPriceTerms = $guaranteedFixedPriceTerms;

    $deal = new Google_Service_AdExchangeBuyerII_Deal();
    $deal->displayName = 'Test Deal #%d' . rand();
    $deal->syndicationProduct = 'GAMES';
    $deal->dealTerms = $dealTerms;

    // Append deals to the proposal that was created earlier.
    $proposal->deals = [$deal];

    // Create proposal.
    $created_proposal = $this->service->accounts_proposals->create(
        $values['buyer_account_id'], $proposal);
    print '<h2>Created Proposal</h2>';
    $this->printResult($created_proposal);
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
    return 'Marketplace: Create Proposal';
  }
}

