make a list of checkmarks that clearly describe all the requested work to be done 

can i make every single run log what it has started/finished/running and when i run any prompt it first checks the log to see if the request has already been done or not and rolls back any unfinished work and continues from there? is that possible and if so, what are the ways to configure ti


saving and continueing progress
first record the commit id before starting to work in this <filename> and then commit every step with appropriate commit message and record them in the <filename> too. 

at the end of the run check if the prompt request has been successfully satisfied by checking the checkmarks. if all the checkmarks are satisfied, only then squash the commits made throughout the progress into a single commit with a clear but brief implementation message. it should look like the commit id before starting the work and then the commit id after the work.
