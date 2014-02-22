Multiverse
==========

This game represents a playground for trying out neat concepts within the BASE space.

Status
------

The game should always `compile` and have a successful `test` result. Even though it's just a *playground* I still treat it as I would any other repo :)

Points of interests
-------------------

Most of my recent work has been around the DDDD framework, specifically in defining the following 4 command types:

1.  **TailCommand:** Command relating to an existing aggregate. May have prerequisites.
2.  **UnconditionalTailCommand:** Command relating to an existing aggregate with an evaluation that is always valid.
3.  **StaticTailCommand:** Unconditional command relating to an existing aggregate which evokes the same events on every evaluation.
4.  **HeadCommand:** Command with an evaluation that describes the creation of a new aggregate. These commands are static by nature.

There has also been some work on creating a DSL for the above. For example:

    val canonicalUser = UserId(UUID.randomUUID)
    val registeredUser = RegisterUser(userId, email, password, verificationCode, instanceId, timestamp) after Commit()
    val deduplicatedUser = (registeredUser
        after DeduplicateUser(userId, canonicalUser, instanceId, timestamp)
        after Commit())
        
For testing:

    val invalidCode = Hash(List.fill(Hash.size)(1.toByte))
    (registeredUser
        cannot VerifyUserEmail(userId, invalidCode, instanceId, timestamp)
        because VerifyUserEmail.VerificationCodeMatch)

Old ideas
---------

Please do not pay too much attention to the query architecture - I have handled this far better in recent (*private :(*) projects, but have not had the chance to merge the architecture back here.
