# FunkySk
An addon to add full support for NoteBlockAPI

Syntax:
```yaml
Syntax:
  Events:
    PlayerRangeStateChangeEvent:
      enabled: true
      patterns:
      - '[FunkySk] [player] range [state] change [(of|for) %-player/songplayer%]'
      eventvalues:
      - SongPlayer
      - Boolean
      - Player
    SongStoppedEvent:
      enabled: true
      patterns:
      - '[FunkySk] [note[ ]block] song (end|destroy|stop)[ing] (with|of|for) id %string%'
      - '[FunkySk] song id stop[ing] %string%'
      eventvalues: []
    SongDestroyingEvent:
      enabled: true
      patterns:
      - '[FunkySk] [note[ ]block] song (end|destroy|stop)[ing] (with|of|for) id %string%'
      - '[FunkySk] song id stop[ing] %string%'
      eventvalues: []
    SongEndEvent:
      enabled: true
      patterns:
      - '[FunkySk] [note[ ]block] song (end|destroy|stop)[ing] (with|of|for) id %string%'
      - '[FunkySk] song id stop[ing] %string%'
      eventvalues: []
  Expressions:
    ExprIDSongs:
      enabled: true
      description: Returns the ID's of all songs currently playing.
      syntax:
      - '[(all [[of] the]|the)] id songs playing'
    ExprPositionSongPlayer:
      enabled: true
      description: Returns new position song player(s) from the given song(s). A position
        song player is a song player that is locked on a location.
      syntax:
      - '[a] [new] position song [music] player[s] from [the] [song[s]] %songs% [with
        %-location%]'
    ExprPlayersInID:
      enabled: true
      changers: All changers
      description: Returns the players currently listening to a song with an ID. Returns
        as a string because that's what NoteBlockAPI does.
      syntax:
      - '[(all [[of] the]|the)] player[s] (in|listening to) song with id[s] %strings%'
    ExprInventorySlots:
      enabled: true
      changers: '[SET]'
      description: A temporary setup for multiple slots while Bensku updates Skript
        with my pull request to add this.
      syntax:
      - '[the] slot[s] %numbers% of %inventory%'
      - '%inventory%[''s] slot[s] %numbers%'
    ExprSongPlayer:
      enabled: true
      description: Returns new song player(s) from the given song(s).
      syntax:
      - '[a] [new] song [music] player[s] from [the] [song[s]] %songs%'
    ExprNoteBlockSongPlayer:
      enabled: true
      description: Returns new noteblock song player(s) from the given song(s). A
        noteblock song player is a song player that is locked on a block which is
        a noteblock.
      syntax:
      - '[a] [new] note[ ]block song [music] player[s] from [the] [song[s]] %songs%
        [with [note[ ]block] %-block%]'
    ExprSong:
      enabled: true
      description: Returns songs based from the file strings. Can accept a file path
        string or a url link.
      syntax:
      - '[a] [new] song[s] [from [the]] (file [path]|url|web)[s] %strings%'
    ExprSongPlayersOfID:
      enabled: true
      description: Returns the song's music player(s) of string(s).
      syntax:
      - '[(all [[of] the]|the)] song[ ]player[s] (from|of) [note[ ]block] id[s] %strings%'
    ExprLayerNote:
      enabled: true
      changers: '[SET]'
      description: Returns a note at the defined tick of the song.
      syntax:
      - '[(all [[of] the]|the)] note[s] of [layer[s]] %layers% [at [tick] %-number%]'
      - '[layer[s]] %layers%[''s] note[s] [at [tick] %-number%]'
    ExprSongsInFile:
      enabled: true
      description: Returns all the .nbs songs in a file location.
      syntax:
      - '[(all [[of] the]|the)] [nbt] song[s] [file[s]] in [the] (directory|file[s])
        %string%'
  Conditions:
    CondIsIDPlaying:
      enabled: true
      description: Check if a song with an ID is playing.
      syntax:
      - (song|track|music|noteblock) [with] id %string% (1¦is|2¦is(n't| not)) playing
    CondPlayerCanHear:
      enabled: true
      description: Check if a song is playing for a user.
      syntax:
      - '%player% (1¦[does] ha(s|ve)|2¦do[es](n''t| not) have) [a[n[y]]] (song|track|music|noteblock)[s]
        playing'
    CondPlayerInRange:
      enabled: true
      description: Check if a minecraft player is in range of the position song player.
      syntax:
      - '%player% (1¦is|2¦is(n''t| not)) in range [of [the]] position song[ ]player
        %positionsongplayer%'
      - '%player% (1¦can|2¦can(n''t| not)) hear [the] position song[ ]player %positionsongplayer%'
    CondSongPlayerPlaying:
      enabled: true
      description: Check if a song is playing for a user.
      syntax:
      - song[ ]player %songplayer% (1¦is|2¦is(n't| not)) playing [a[n[y]]] (song|track|music|noteblock)[s]
  Effects:
    EffPositionSongPlayerTick:
      enabled: true
      description: Play a tick of a song to the user(s).
      syntax:
      - play tick %number% from [position song[ ]player] %positionsongplayer% to %players%
    EffPlayerStopPlaying:
      enabled: true
      description: Stops a song from being played to the minecraft player(s). Doesn't
        seem to work well, use the player list of a song player.
      syntax:
      - stop [a[n[y]]] (song|track|music|noteblock) [from [being] playing] (for|to)
        %players%
    EffPauseSong:
      enabled: true
      description: Pause a song with an ID.
      syntax:
      - (stop|pause) [note[[ ]block]] song[s] with id[s] %strings%
    EffPlaySong:
      enabled: true
      description: Play a song with an ID.
      syntax:
      - (resume|play|continue) [note[[ ]block]] song[s] with id[s] %strings%
    EffDestroySongPlayer:
      enabled: true
      description: Destory/Remove the song player(s).
      syntax:
      - (destroy|remove) [note[[ ]block]] song[ ]player[s] %songplayers%
    EffStartSong:
      enabled: true
      description: Plays a new song for a user with an optional ID system to be able
        to easily manage the song being played.
      syntax:
      - (start|play) [new] [note[[ ]block]] [song] %song% to %players% [with id %-string%]
  PropertyExpressions:
    ExprPositionSongPlayerDistance:
      enabled: true
      changers: '[SET]'
      description: Returns the distance that the position song player(s) can be heard
        from. The default distance is 16.
      syntax:
      - '[(all [[of] the]|the)] [sound] (distance|range)[s] of position song[ ]player[s]
        %positionsongplayers%'
      - '%positionsongplayers%[''s] position song[ ]player[s] [sound] (distance|range)[s]'
    ExprPlayerVolume:
      enabled: true
      changers: '[SET]'
      description: Returns the volume of the user(s). This is like a preference system
        for each minecraft user.
      syntax:
      - '[(all [[of] the]|the)] sound volume[s] of [player[s]] %players%'
      - '%players%[''s] [player[s]] sound volume[s]'
    ExprSongDescription:
      enabled: true
      description: Returns the description of the song(s).
      syntax:
      - '[(all [[of] the]|the)] desc[ription[s]] of song[s] %songs%'
      - '%songs%[''s] song[s] desc[ription[s]]'
    ExprSongHeight:
      enabled: true
      description: Returns the height of the song(s).
      syntax:
      - '[(all [[of] the]|the)] height[s] of song[s] %songs%'
      - '%songs%[''s] song[s] height[s]'
    ExprSongPlayerVolume:
      enabled: true
      changers: '[SET]'
      description: Returns the volume of the song player(s).
      syntax:
      - '[(all [[of] the]|the)] volume[s] of song[ ]player[s] %songplayers%'
      - '%songplayers%[''s] song[ ]player[s] volume[s]'
    ExprSongPlayerTick:
      enabled: true
      changers: '[SET]'
      description: Returns the tick that the song player(s) are currently on.
      syntax:
      - '[(all [[of] the]|the)] [playing] tick[s] of song[ ]player[s] %songplayers%'
      - '%songplayers%[''s] song[ ]player[s] [playing] tick[s]'
    ExprSongPlayerSong:
      enabled: true
      description: Returns the song currently playing in the song player(s).
      syntax:
      - '[(all [[of] the]|the)] (song|track|music)[s] of song[ ]player[s] %songplayers%'
      - '%songplayers%[''s] song[ ]player[s] (song|track|music)[s]'
    ExprSongPlayerFadeTarget:
      enabled: true
      changers: '[SET]'
      description: Returns the fade target of the song player(s).
      syntax:
      - '[(all [[of] the]|the)] fad(e|ing)[ ]target[s] of [song[ ]player[s]] %songplayers%'
      - '%songplayers%[''s] [song[ ]player[s]] fad(e|ing)[ ]target[s]'
    ExprSongDelay:
      enabled: true
      description: Returns the delay of the song(s) if they have a delay.
      syntax:
      - '[(all [[of] the]|the)] delay[s] of song[s] %songs%'
      - '%songs%[''s] song[s] delay[s]'
    ExprSongPlayerFadeDuration:
      enabled: true
      changers: '[SET]'
      description: Returns the fade duration time of the song player(s).
      syntax:
      - '[(all [[of] the]|the)] fad(e|ing)[ ](duration|time)[s] of [song[ ]player[s]]
        %songplayers%'
      - '%songplayers%[''s] [song[ ]player[s]] fad(e|ing)[ ](duration|time)[s]'
    ExprNoteBlockSongPlayerDistance:
      enabled: true
      changers: '[SET]'
      description: Returns the distance that the noteblock song player(s) can be heard
        from. The default distance is 16.
      syntax:
      - '[(all [[of] the]|the)] [sound] (distance|range)[s] of note[ ]block song[
        ]player[s] %noteblocksongplayers%'
      - '%noteblocksongplayers%[''s] note[ ]block song[ ]player[s] [sound] (distance|range)[s]'
    ExprSongSpeed:
      enabled: true
      description: Returns the speed/tempo of the song(s).
      syntax:
      - '[(all [[of] the]|the)] (tempo|speed)[s] of song[s] %songs%'
      - '%songs%[''s] song[s] (tempo|speed)[s]'
    ExprSongPlayerFade:
      enabled: true
      changers: '[SET]'
      description: Returns the fade type of the song player(s). The fade type is how
        the song fades. Currently there is only linear
      syntax:
      - '[(all [[of] the]|the)] fad(e|ing)[[ ]type][s] of [song[ ]player[s]] %songplayers%'
      - '%songplayers%[''s] [song[ ]player[s]] fad(e|ing)[[ ]type][s]'
    ExprSongPlayerCategory:
      enabled: true
      changers: '[SET]'
      description: Returns the song currently playing in the song player(s).
      syntax:
      - '[(all [[of] the]|the)] (song|track|music)[s] of song[ ]player[s] %songplayers%'
      - '%songplayers%[''s] song[ ]player[s] (song|track|music)[s]'
    ExprSongTitle:
      enabled: true
      description: Returns the title of the song(s).
      syntax:
      - '[(all [[of] the]|the)] (name|title)[s] of song[s] %songs%'
      - '%songs%[''s] song[s] (name|title)[s]'
    ExprSongAuthor:
      enabled: true
      description: Returns the author of the song(s).
      syntax:
      - '[(all [[of] the]|the)] (author|creator)[s] of song[s] %songs%'
      - '%songs%[''s] song[s] (author|creator)[s]'
    ExprSongPlayerFadeFinish:
      enabled: true
      changers: '[SET]'
      description: Returns the fade finishing time of the song player(s).
      syntax:
      - '[(all [[of] the]|the)] fad(e|ing)[ ]((finish|end)[ing]|done) [time][s] of
        [song[ ]player[s]] %songplayers%'
      - '%songplayers%[''s] [song[ ]player[s]] fad(e|ing)[ ]((finish|end)[ing]|done)
        [time][s]'
    ExprPositionSongPlayerTarget:
      enabled: true
      changers: '[SET]'
      description: Returns the location of the position song player(s).
      syntax:
      - '[(all [[of] the]|the)] (target [location]|location)[s] of position song[
        ]player[s] %positionsongplayers%'
      - '%positionsongplayers%[''s] position song[ ]player[s] (target [location]|location)[s]'
    ExprNoteBlockSongPlayerBlock:
      enabled: true
      changers: '[SET]'
      description: Returns the actual noteblock block of the noteblock song player(s).
      syntax:
      - '[(all [[of] the]|the)] [note[ ]]block[s] of position song[ ]player[s] %noteblocksongplayers%'
      - '%noteblocksongplayers%[''s] position song[ ]player[s] [note[ ]]block[s]'
    ExprSongLength:
      enabled: true
      description: Returns the length of the song(s).
      syntax:
      - '[(all [[of] the]|the)] length[s] of song[s] %songs%'
      - '%songs%[''s] song[s] length[s]'
    ExprSongPlayerListeners:
      enabled: true
      changers: All changers
      description: Returns the listeners of the song player(s).
      syntax:
      - '[(all [[of] the]|the)] (player|listener)[s] of song[ ]player[s] %songplayers%'
      - '%songplayers%[''s] song[ ]player[s] (player|listener)[s]'
    ExprSongPlayerPlaying:
      enabled: true
      changers: '[SET]'
      description: Returns if the song player(s) are playing or not.
      syntax:
      - '[(all [[of] the]|the)] [music] playing [state[s]] of song[ ]player[s] %songplayers%'
      - '%songplayers%[''s] song[ ]player[s] [music] playing [state[s]]'
    ExprSongPlayerAutoDestory:
      enabled: true
      changers: '[SET]'
      description: Returns if the song player(s) should destroy on finish.
      syntax:
      - '[(all [[of] the]|the)] [auto] destroy[s] of song[ ]player[s] %songplayers%'
      - '%songplayers%[''s] song[ ]player[s] [auto] destroy[s]'
    ExprSongPlayerFadeStart:
      enabled: true
      changers: '[SET]'
      description: Returns the fade starting time of the song player(s).
      syntax:
      - '[(all [[of] the]|the)] fad(e|ing)[ ]start[ing] [time][s] of [song[ ]player[s]]
        %songplayers%'
      - '%songplayers%[''s] [song[ ]player[s]] fad(e|ing)[ ]start[ing] [time][s]'
    ExprSongLayer:
      enabled: true
      description: Returns the layers of the song(s). The layers are like a chart
        of all the notes.
      syntax:
      - '[(all [[of] the]|the)] layers of [song[s]] %song%'
      - '%song%[''s] [song[s]] layers'
    ExprLayerName:
      enabled: true
      changers: '[SET]'
      description: Returns the name of the layer(s).
      syntax:
      - '[(all [[of] the]|the)] name[s] of layer[s] %layers%'
      - '%layers%[''s] layer[s] name[s]'
    ExprLayerVolume:
      enabled: true
      changers: '[SET]'
      description: Returns the volume of the layer(s).
      syntax:
      - '[(all [[of] the]|the)] volume[s] of layer[s] %layers%'
      - '%layers%[''s] layer[s] volume[s]'
    ExprNotePitch:
      enabled: true
      description: Returns the pitch(s) of the Note(s). The return number is from
        a byte.
      syntax:
      - '[(all [[of] the]|the)] pitch[s] of note[s] %notes%'
      - '%notes%[''s] note[s] pitch[s]'
    ExprNoteInstrument:
      enabled: true
      changers: '[SET]'
      description: Returns the instrument(s) of the Note(s). The return number is
        from a byte.
      syntax:
      - '[(all [[of] the]|the)] instrument[s] of [note[s]] %notes%'
      - '%notes%[''s] [note[s]] instrument[s]'
    ExprNoteKey:
      enabled: true
      changers: '[SET]'
      description: Returns the key(s) of the Note(s). The return number is from a
        byte. Key is what can be used to get the pitch. Subtract it by 33 or just
        use the pitch from note syntax.
      syntax:
      - '[(all [[of] the]|the)] key[s] of note[s] %notes%'
      - '%notes%[''s] note[s] key[s]'
  Enums:
    FadeType:
      names:
      - fade linear, fade straight, linear, straight
      user: fadetypes?
    SoundCategory:
      names:
      - master
      - music
      - record, records
      - weather
      - block, blocks
      - hostile, monsters
      - neutral
      - player, players
      - ambient
      - voice, narrator
      user: soundcategor(y|ies)
```
