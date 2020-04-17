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

"""This example accepts the given proposal at the given revision."""

import argparse
import os
import pprint
import sys

sys.path.insert(0, os.path.abspath('..'))

from googleapiclient.errors import HttpError
import samples_util


DEFAULT_ACCOUNT_ID = 'INSERT_ACCOUNT_ID'
DEFAULT_PROPOSAL_ID = 'INSERT_PROPOSAL_ID'


def main(ad_exchange_buyer, account_id, proposal_id, proposal_revision):
  # Construct the request body.
  body = {'proposalRevision': proposal_revision}

  try:
    # Construct and execute the request.
    response = ad_exchange_buyer.accounts().proposals().accept(
        accountId=account_id, proposalId=proposal_id, body=body).execute()
    print(f'Successfully accepted proposal with ID "{proposal_id}":')
    pprint.pprint(response)
  except HttpError as e:
    print(e)


if __name__ == '__main__':
  # For optional arguments, default values are overridden if set.
  parser = argparse.ArgumentParser(description='Accepts the given proposal.')
  parser.add_argument('-a', '--account_id', required=False, type=int,
                      default=DEFAULT_ACCOUNT_ID,
                      help=('The integer id of the account you\'re using to '
                            'create the proposal.'))
  parser.add_argument('-i', '--proposal_id', required=False,
                      default=DEFAULT_PROPOSAL_ID,
                      help=('The ID of the proposal to be accepted.'))
  parser.add_argument('-r', '--proposal_revision', required=True, type=int,
                      help=('The integer revision of the proposal being '
                            'accepted. This is a required field.'))
  args = parser.parse_args()

  try:
    service = samples_util.GetService('v2beta1')
  except IOError as ex:
    print(f'Unable to create adexchangebuyer service - {ex}')
    print('Did you specify the key file in samples_util.py?')
    sys.exit(1)

  main(service, args.account_id, args.proposal_id, args.proposal_revision)

