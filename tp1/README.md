# SS TP1 Cell Index Method

This program was build with Java and JFrame (for the observations)

In this TP, there must be a `Static.txt` file to run the program. This file must only contain numbers, spaces or  some particular characters like `.` and `e`.

The `Static.txt` file has defined elements that must appear in different rows that are explained below:

- `L`: the size of each side

- `r_c`: the radio of interaction

- `M`: the value used to generate the cells.

- `N`: the amoutn of particles to generate.

The dafault value for the particle's radius is `0`. It can be setted a common value by adding on the file a row starting with `r`

The default value of particles is `100` if it is not specify.

At the end of the file, you can add particles by providing in the same row at least the x and y value of the particle in the same row separating the values of the particle with a space (do not add any other character in this row that is not either a nnumber, a space or `.` for floating numbers)