#!/usr/bin/python
#
# Copyright 2015 Google Inc. All Rights Reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

"""This example demonstrates how to do a sparse update of Account attributes.

Tags: Accounts.patch
"""

__author__ = 'api.msaniscalchi@gmail.com (Mark Saniscalchi)'

import argparse
import pprint
import sys

from oauth2client.client import AccessTokenRefreshError
import util

# Optional arguments; overrides default values if set.
parser = argparse.ArgumentParser(description='Updates an existing account.')
parser.add_argument('-a', '--account_id', required=False, type=int,
                    help=('The integer id of the account you\'re updating.'))
parser.add_argument('-c', '--cookie_matching_url', required=False,
                    default='https://www.google.com',
                    help=('The base URL used in cookie match requests.'))


def main(ad_exchange_buyer, account_id, body):
  # Construct the request.
  request = ad_exchange_buyer.accounts().patch(id=account_id, body=body)

  # Execute request and print response.
  pprint.pprint(request.execute())

if __name__ == '__main__':
  try:
    service = util.GetService()
    args = parser.parse_args()

    # Create a body containing the fields to be updated.
    # This example only updates the cookieMatchingUrl.
    if args.account_id:
      BODY = {
          'accountId': args.account_id,
          'cookieMatchingUrl': args.cookie_matching_url
      }
    else:
      BODY = {
          'accountId': int('INSERT_ACCOUNT_ID'),
          'cookieMatchingUrl': 'INSERT_COOKIE_MATCHING_URL'
      }

    if BODY['cookieMatchingUrl'] == 'INSERT_COOKIE_MATCHING_URL':
      raise Exception('The cookieMatchingUrl was not set.')
  except IOError, ex:
    print 'Unable to create adexchangebuyer service - %s' % ex
    print 'Did you specify the key file in util.py?'
    sys.exit()
  except AccessTokenRefreshError, ex:
    print 'Unable to create adexchangebuyer service - %s' % ex
    print 'Did you set the correct Service Account Email in util.py?'
    sys.exit()
  except ValueError, ex:
    print 'Unable to create adexchangebuyer service - %s' % ex
    print 'Did you set account_id to an integer?'
    sys.exit()

  main(service, BODY['accountId'], BODY)
