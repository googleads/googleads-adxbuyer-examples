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

"""This example submits a simplified HTMLSnippet creative for verification."""

import argparse
import pprint
import os
import sys

sys.path.insert(0, os.path.abspath('..'))

from googleapiclient.errors import HttpError
import samples_util


DEFAULT_ACCOUNT_ID = 'INSERT_ACCOUNT_ID'
DEFAULT_ADVERTISER_NAME = 'INSERT_ADVERTISER_NAME'
DEFAULT_BUYER_CREATIVE_ID = 'INSERT_BUYER_CREATIVE_ID'
DEFAULT_CLICK_THROUGH_URL = 'INSERT_CLICK_THROUGH_URL'
DEFAULT_HEIGHT = 'INSERT_HEIGHT'
DEFAULT_HTML_SNIPPET = 'INSERT_HTML_SNIPPET'
DEFAULT_WIDTH = 'INSERT_WIDTH'


def main(ad_exchange_buyer, body):
  try:
    # Construct and execute the request.
    creative = ad_exchange_buyer.creatives().insert(body=body).execute()
    print 'Successfully inserted creative.'
    pprint.pprint(creative)
  except HttpError as e:
    print e


if __name__ == '__main__':
  # Optional arguments; overrides default values if set.
  parser = argparse.ArgumentParser(description='Inserts a new creative into '
                                   'the verification pipeline.')
  parser.add_argument('-a', '--account_id', required=False, type=int,
                      default=DEFAULT_ACCOUNT_ID,
                      help=('The integer id of the account you\'re using to '
                            'insert the creative.'))
  parser.add_argument('-n', '--advertiser_name', required=False,
                      default=DEFAULT_ADVERTISER_NAME,
                      help=('The name of the company being advertised by the '
                            'creative.'))
  parser.add_argument('-b', '--buyer_creative_id', required=False,
                      default=DEFAULT_BUYER_CREATIVE_ID,
                      help=('The buyerCreativeId of the creative you want to '
                            'retrieve.'))
  parser.add_argument('-c', '--click_through_url', required=False,
                      default=DEFAULT_CLICK_THROUGH_URL,
                      help=('A destination URL for the snippet.'))
  parser.add_argument('-ht', '--height', required=False, type=int,
                      default=DEFAULT_HEIGHT,
                      help=('The height of the creative in pixels.'))
  parser.add_argument('-s', '--html_snippet', required=False,
                      default=DEFAULT_HTML_SNIPPET,
                      help=('The HTML snippet that displays the ad when '
                            'inserted in the web page.'))
  parser.add_argument('-w', '--width', required=False, type=int,
                      default=DEFAULT_WIDTH,
                      help=('The width of the creative in pixels.'))
  args = parser.parse_args()

  # Create a body containing the required fields.
  BODY = {
      'accountId': args.account_id,
      'advertiserName': args.advertiser_name,
      'buyerCreativeId': args.buyer_creative_id,
      'clickThroughUrl': [args.click_through_url],
      'height': args.height,
      'HTMLSnippet': args.html_snippet,
      'width': args.width
  }

  try:
    service = samples_util.GetService()
  except IOError, ex:
    print 'Unable to create adexchangebuyer service - %s' % ex
    print 'Did you specify the key file in samples_util.py?'
    sys.exit(1)

  main(service, BODY)

