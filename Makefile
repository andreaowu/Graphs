# This a Makefile, an input file for the 'make' program.  For you 
# command-line and Emacs enthusiasts, this makes it possible to build
# this program with a single command:
#     gmake 
# (or just 'make' if you are on a system that uses GNU make by default,
# such as Linux.) You can also clean up junk files and .class files with
#     gmake clean
# To run style61b (our style enforcer) over your source files, type
#     gmake style
# Finally, you can run tests with
#     gmake check

# This is not an especially efficient Makefile, because it's not easy to
# figure out the minimal set of Java files that need to be recompiled.  
# So if any .class file does not exist or is older than its .java file,
# we just remove all the .class files, compile the main class, and 
# then compile everything in the plugin directory.  

GRAPH_SRCS := $(wildcard graph/*.java)
MAKE_SRCS := $(wildcard make/*.java)
TRIP_SRCS := $(wildcard trip/*.java)

# All source files
SRCS := $(GRAPH_SRCS) $(MAKE_SRCS) $(TRIP_SRCS)

# Sources for the public entry points to the system.
MAIN_SRCS = trip/Main.java make/Main.java

# Unit test files.
TESTING_SRCS = graph/Testing.java trip/Testing.java 

# All other Java sources (see also Project 1 Makefile).
OTHER_SRCS := $(filter-out $(MAIN_SRCS) $(TESTING_SRCS), $(SRCS))

# Test-spec files
ALL_TESTS = $(wildcard make-tests/*.tst) $(wildcard trip-tests/*.tst)

# Flags to pass to Java compilations (include debugging info and report
# "unsafe" operations.)
JFLAGS = -g -Xlint:unchecked -Xlint:deprecation

CLASSES = $(SRCS:.java=.class)
MAIN_CLASSES = $(MAIN_SRCS:.java=.class)
TESTING_CLASSES = $(TESTING_SRCS:.java=.class)
OTHER_CLASSES = $(OTHER_SRCS:.java=.class)

# Tell make that these are not really files.
.PHONY: clean default check regression-test unit-test style

# By default, make sure all classes are present and check if any sources have
# changed since the last build.
default: $(MAIN_CLASSES) $(TESTING_CLASSES)

# If any class is missing, or any source changed since the main classes were
# compiled, remove all class files and recompile.
$(MAIN_CLASSES): $(MAIN_SRCS) $(OTHER_SRCS)
	$(RM) $(CLASSES)
	javac $(JFLAGS) $(MAIN_SRCS) || { $(RM) $(CLASSES); false; }

$(TESTING_CLASSES): $(MAIN_CLASSES) $(TESTING_SRCS)
	javac $(JFLAGS) $(TESTING_SRCS)

check: $(CLASSES)
	$(MAKE) -C graph check
	$(MAKE) -C make check
	$(MAKE) -C trip check

# Check style of source files with style61b.
style:
	style61b $(STYLEFLAGS) $(SRCS)

# Find and remove all *~, *.class, and testing output files.
# Do not touch .svn directories.
clean :
	$(RM) *~
	$(MAKE) -C graph clean
	$(MAKE) -C trip clean
	$(MAKE) -C make clean
