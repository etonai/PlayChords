Here is the current list of progressions in Song Scaffold:

    val chordProgressions: List<ChordProgression> = listOf(
        chordProgression("Classic Cadence",        "Classic / Standard",      listOf("I", "IV", "V", "I"), CADENTIAL),
        chordProgression("Pop Axis",               "Classic / Standard",      listOf("I", "V", "vi", "IV"), LIFT, LOOP),
        chordProgression("Minor Pop Loop",         "Classic / Standard",      listOf("vi", "IV", "I", "V"), OPEN, LIFT, LOOP),

        chordProgression("Two Five One",           "Musical Theatre / Jazz",  listOf("ii", "V", "I"), CADENTIAL),
        chordProgression("Circle Turnaround",      "Musical Theatre / Jazz",  listOf("I", "vi", "ii", "V"), OPEN, CADENTIAL),
        chordProgression("Extended Turnaround",    "Musical Theatre / Jazz",  listOf("iii", "vi", "ii", "V"), OPEN, PIVOT),
        chordProgression("Borrowed Minor Four",    "Musical Theatre / Jazz",  listOf("I", "Imaj7", "IV", "iv"), COLOR, PIVOT),
        chordProgression("Dominant Lift Setup",    "Musical Theatre / Jazz",  listOf("IV", "I", "II7", "V"), OPEN, LIFT, PIVOT, COLOR),

        chordProgression("Secondary Dominant Lift","Expressive / Color",      listOf("I", "V/vi", "vi", "IV"), LIFT, COLOR),
        chordProgression("Major To Minor Four",    "Expressive / Color",      listOf("I", "IV", "iv", "I"), CADENTIAL, COLOR),
        chordProgression("Minor To Resolution",    "Expressive / Color",      listOf("vi", "ii", "V", "I"), CADENTIAL, LIFT),
        chordProgression("Flat Seven Color",       "Expressive / Color",      listOf("I", "♭VII", "IV", "I"), COLOR, CADENTIAL),

        chordProgression("Two Chord Open Loop",    "Loops",                   listOf("I", "IV"), OPEN, LOOP),
        chordProgression("Two Five Loop",          "Loops",                   listOf("ii", "V"), OPEN, LOOP),

        chordProgression("50s Progression",          "Classic / Standard",      listOf("I", "vi", "IV", "V"), LIFT, LOOP),
        chordProgression("Plagal Loop",              "Classic / Standard",      listOf("IV", "I", "V", "I"), CADENTIAL, LIFT),
        chordProgression("Descending Bass Line",     "Classic / Standard",      listOf("I", "V/7", "vi", "V"), OPEN, COLOR),
        chordProgression("Axis Variant",             "Classic / Standard",      listOf("vi", "V", "IV", "I"), CADENTIAL, LIFT),

        chordProgression("Backdoor Resolution",      "Musical Theatre / Jazz",  listOf("ii", "♭VII", "I"), CADENTIAL, COLOR),
        chordProgression("Rhythm Changes",           "Musical Theatre / Jazz",  listOf("I", "vi", "ii", "V"), OPEN, CADENTIAL),
        chordProgression("Minor Two Five One",       "Musical Theatre / Jazz",  listOf("iiø", "V7", "i"), CADENTIAL, COLOR),
        chordProgression("Chromatic Walk-Up",        "Musical Theatre / Jazz",  listOf("I", "I#dim", "ii", "V"), PIVOT, COLOR),
        chordProgression("Major Six Turnaround",     "Musical Theatre / Jazz",  listOf("I", "VI", "ii", "I"), OPEN, LOOP, COLOR, PIVOT),

        chordProgression("Line Cliche Major",        "Expressive / Color",      listOf("I", "Imaj7", "I7", "IV"), OPEN, COLOR, PIVOT),
        chordProgression("Mixolydian Variant",       "Expressive / Color",      listOf("I", "♭VII", "I", "IV"), COLOR, LOOP),
        chordProgression("Chromatic Mediants",       "Expressive / Color",      listOf("I", "♭III", "IV", "I"), COLOR, LIFT),
        chordProgression("Deceptive Cycle",          "Expressive / Color",      listOf("V", "vi", "IV", "I"), CADENTIAL, PIVOT),

        chordProgression("Drone Loop",               "Loops",                   listOf("I", "♭VII"), OPEN, LOOP, COLOR),
        chordProgression("Suspended Loop",           "Loops",                   listOf("I", "Vsus4"), OPEN, LOOP),
        chordProgression("Minor Oscillation",        "Loops",                   listOf("i", "♭VI"), OPEN, LOOP, COLOR),
        chordProgression("Pedal Loop",               "Loops",                   listOf("I", "ii/I", "IV/I", "V/I"), OPEN, LOOP, COLOR),

        chordProgression("Epic Rise",                "Cinematic / Modern",      listOf("vi", "IV", "I", "V"), LIFT, LOOP),
        chordProgression("Modern Film Loop",         "Cinematic / Modern",      listOf("i", "♭VI", "III", "♭VII"), OPEN, LOOP, COLOR),
        chordProgression("Lydian Lift",              "Cinematic / Modern",      listOf("I", "II", "IV", "I"), LIFT, COLOR),
        chordProgression("Ambiguous Loop",           "Cinematic / Modern",      listOf("I", "V", "ii", "IV"), OPEN, LOOP, PIVOT),

        chordProgression("Easy",                     "Musical Theatre / Jazz",  listOf("I", "iii", "ii", "V"), OPEN, LIFT, PIVOT),
        chordProgression("Mostly Me",                "Musical Theatre / Jazz",  listOf("I", "IV", "ii", "V"), LIFT, CADENTIAL),
        chordProgression("Love is an Open Door",     "Musical Theatre / Jazz",  listOf("I", "I/3", "IV", "V"), OPEN),
        chordProgression("Stepwise Lift",            "Classic / Standard",      listOf("I", "ii", "IV", "V"), LIFT, OPEN),
        chordProgression("Sal Tlay",                 "Cinematic / Modern",      listOf("I", "iii", "IV", "V"), LIFT, OPEN, COLOR),
        chordProgression("Who Knew",                 "Expressive / Color",      listOf("I", "ii", "vi", "V"), OPEN, PIVOT, COLOR),
        chordProgression("Thirds Descent",           "Expressive / Color",      listOf("I", "iii", "vi", "V"), OPEN, COLOR, PIVOT),
        chordProgression("More Than a Feeling",      "Classic / Standard",      listOf("I", "IV", "vi", "V"), LIFT, LOOP, OPEN),
        chordProgression("Ascending Walk",           "Classic / Standard",      listOf("I", "ii", "iii", "V"), LIFT, OPEN),
        chordProgression("Four Three Drop",          "Expressive / Color",      listOf("I", "IV", "iii", "V"), COLOR, PIVOT),
        chordProgression("Life Would Suck",          "Expressive / Color",      listOf("I", "vi", "iii", "V"), OPEN, COLOR, LOOP)


    )


The goal of this would be to incorporate all of these Chord progressions into PlayChords.
If there is a conflict, the Progression Name from SongScafford takes priority.
The list of tags should be combined
The Category from Song Scaffold takes priority
