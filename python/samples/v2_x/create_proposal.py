#!/usr/bin/python
#
# Copyright 2018 Google LLC
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#    https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

"""This example illustrates how to create a proposal."""

import argparse
import os
import pprint
import sys
import uuid

sys.path.insert(0, os.path.abspath('..'))

from googleapiclient.errors import HttpError
import samples_util


DEFAULT_BUYER_ACCOUNT_ID = 'INSERT_ACCOUNT_ID'
DEFAULT_SELLER_ACCOUNT_ID = 'INSERT_ACCOUNT_ID'


def main(ad_exchange_buyer, account_id, body):
  try:
    # Create the proposal.
    request = ad_exchange_buyer.accounts().proposals().create(
        accountId=account_id, body=body)
    response = request.execute()
    print(f'Successfully created proposal for buyer accountId "{account_id}":')
    pprint.pprint(response)
  except HttpError as e:
    print(e)


if __name__ == '__main__':
  # For optional arguments, default values are overridden if set.
  parser = argparse.ArgumentParser(
      description='Creates a new proposal for a programmatic guaranteed deal.')
  parser.add_argument('-b', '--buyer_account_id', required=False,
                      default=DEFAULT_BUYER_ACCOUNT_ID,
                      help=('The integer id of the account you\'re using to '
                            'create the proposal.'))
  parser.add_argument('-s', '--seller_account_id', required=False,
                      default=DEFAULT_SELLER_ACCOUNT_ID,
                      help=('The integer accountId of the seller for which '
                            'you are making the proposal.'))
  parser.add_argument('--seller_sub_account_id', required=False,
                      help=('The optional integer subAccountId of the seller '
                            'for which you are making the proposal.'))
  args = parser.parse_args()

  try:
    service = samples_util.GetService('v2beta1')
  except IOError as ex:
    print(f'Unable to create adexchangebuyer service - {ex}')
    print('Did you specify the key file in samples_util.py?')
    sys.exit(1)

  # Build the proposal for the proposals.create request.
  create_proposal_body = {
      'buyer': {
          'accountId': args.buyer_account_id
      },
      'displayName': f'Test Proposal #{uuid.uuid4()}',
      'seller': {
          'accountId': args.seller_account_id
      },
      # Add the Programmatic Guaranteed deal that will be associated with the
      # proposal.
      'deals': [{
          'displayName': f'Test Deal #{uuid.uuid4()}',
          'syndicationProduct': 'GAMES',
          'dealTerms': {
              'description': 'Test deal.',
              'brandingType': 'BRANDED',
              'sellerTimeZone': 'America/New_York',
              'estimatedGrossSpend': {
                  'pricingType': 'COST_PER_MILLE',
                  'amount': {
                      'currencyCode': 'USD',
                      'units': '0',
                      'nanos': '1'
                  }
              },
              'estimatedImpressionsPerDay': '1',
              # The pricing terms used in this example are guaranteed fixed
              # price terms, making this a programmatic guaranteed deal.
              # Alternatively, you could use non-guaranteed fixed price terms
              # to specify a preferred deal. Private auction deals use
              # non-guaranteed auction terms; however, only sellers can create
              # this deal type.
              'guaranteedFixedPriceTerms': {
                  'guaranteedLooks': '1',
                  'guaranteedImpressions': '1',
                  'fixedPrices': [{
                      'buyer': {
                          'accountId': args.buyer_account_id
                      },
                      'price': {
                          'pricingType': 'COST_PER_MILLE',
                          'amount': {
                              'currencyCode': 'USD',
                              'units': '0',
                              'nanos': '1'
                          }
                      }
                  }],
                  'minimumDailyLooks': '1'
              }
          }
      }]
  }

  if args.seller_sub_account_id:
    create_proposal_body['seller']['subAccountId'] = args.seller_sub_account_id

  main(service, args.buyer_account_id, create_proposal_body)
