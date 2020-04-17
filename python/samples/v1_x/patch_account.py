#!/usr/bin/python
#
# Copyright 2016 Google Inc. All Rights Reserved.
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

"""This example performs a sparse update of account attributes."""

import argparse
import pprint
import os
import sys

sys.path.insert(0, os.path.abspath('..'))

from googleapiclient.errors import HttpError
import samples_util


DEFAULT_ACCOUNT_ID = 'INSERT_ACCOUNT_ID'
DEFAULT_COOKIE_MATCHING_URL = 'INSERT_COOKIE_MATCHING_URL'


def main(ad_exchange_buyer, account_id, body):
  try:
    # Construct and execute the request.
    account = ad_exchange_buyer.accounts().patch(id=account_id,
                                                 body=body).execute()
    print(f'Patched Account "{account_id}".')
    pprint.pprint(account)
  except HttpError as e:
    print(e)


if __name__ == '__main__':
  # Optional arguments; overrides default values if set.
  parser = argparse.ArgumentParser(description='Updates an existing '
                                               'account.')
  parser.add_argument('-a', '--account_id', required=False, type=int,
                      default=DEFAULT_ACCOUNT_ID,
                      help=('The integer id of the account you\'re '
                            'updating.'))
  parser.add_argument('-c', '--cookie_matching_url', required=False,
                      default=DEFAULT_COOKIE_MATCHING_URL,
                      help=('The base URL used in cookie match requests.'))
  args = parser.parse_args()
  # Create a body containing the fields to be updated.
  # This example only updates the cookieMatchingUrl.
  BODY = {
      'accountId': args.account_id,
      'cookieMatchingUrl': args.cookie_matching_url
  }

  try:
    service = samples_util.GetService()
  except IOError as ex:
    print(f'Unable to create adexchangebuyer service - {ex}')
    print('Did you specify the key file in samples_util.py?')
    sys.exit(1)

  main(service, BODY['accountId'], BODY)

