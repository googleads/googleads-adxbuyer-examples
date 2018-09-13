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

"""This example demonstrates how to retrieve a Creative."""


import argparse
import pprint
import os
import sys

sys.path.insert(0, os.path.abspath('..'))

from googleapiclient.errors import HttpError
import samples_util


DEFAULT_ACCOUNT_ID = 'INSERT_ACCOUNT_ID'
DEFAULT_BUYER_CREATIVE_ID = 'INSERT_BUYER_CREATIVE_ID'


def main(ad_exchange_buyer, account_id, buyer_creative_id):
  try:
    # Construct and execute the request.
    creative = ad_exchange_buyer.creatives().get(
        accountId=account_id,
        buyerCreativeId=buyer_creative_id).execute()
    pprint.pprint(creative)
  except HttpError as e:
    print e


if __name__ == '__main__':
  # Optional arguments; overrides default values if set.
  parser = argparse.ArgumentParser(description='Gets the status for a single '
                                   'creative.')
  parser.add_argument('-a', '--account_id', required=False, type=int,
                      default=DEFAULT_ACCOUNT_ID,
                      help=('The integer id of the account you\'re retrieving '
                            'the creative from.'))
  parser.add_argument('-b', '--buyer_creative_id', required=False,
                      default=DEFAULT_BUYER_CREATIVE_ID,
                      help=('The buyerCreativeId of the creative you want to '
                            'retrieve.'))
  args = parser.parse_args()

  try:
    service = samples_util.GetService()
  except IOError, ex:
    print 'Unable to create adexchangebuyer service - %s' % ex
    print 'Did you specify the key file in samples_util.py?'
    sys.exit(1)

  main(service, args.account_id, args.buyer_creative_id)

