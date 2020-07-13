# _*THIS IS A FORK OF THE ORIGINAL PROJECT*_

This project is to aggregate all the data from nflscrapR into a PostgreSQL database. Please see the [Releases](https://github.com/brayellison/nflscrapR-data/releases) page for the generated database.

# `nflscrapR`-data repository

This repository contains both data accessed from NFL.com using [`nflscrapR`](https://github.com/maksimhorowitz/nflscrapR) along with all of the statistics generated by the `nflscrapR` [expected points
and win probability models](https://arxiv.org/abs/1802.00998) (source code available [here](https://github.com/ryurko/nflscrapR-models)).

The data folders are organized in the following manner (will be updating):

+ [play_by_play_data](https://github.com/ryurko/nflscrapR-data/blob/master/play_by_play_data) - all play-by-play 
data accessed with `nflscrapR`, with three folders for pre-, post-, and regular season games.
+ [games_data](https://github.com/ryurko/nflscrapR-data/blob/master/games_data) - all game data accessed
with the `nflscrapR::scrape_game_ids` function containing info such as the home and away team,
score, game's URL, with three folders for pre-, post-, and regular season games.
+ [legacy_data](https://github.com/ryurko/nflscrapR-data/blob/master/legacy_data) - all data accessed and generated with previous version of `nflscrapR`  

Additionally, code examples are located in the [R folder](https://github.com/ryurko/nflscrapR-data/blob/master/R).

During the 2018 NFL season, this repository will be updated at least once a week on
either Tuesday or Wednesday to account for each week of games played.
