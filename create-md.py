import yaml
with open('algorithms/pgx_builtin_a1_prim.yml', 'r') as yml:
    config = yaml.load(yml, Loader=yaml.FullLoader)
 
print('# ', config['name'], sep='')
print()

print('- **Category:** ', config['category'], sep='')
print('- **Time Complexity:** ', config['complexity']['time'], sep='')
print('- **Space Requirement:** ', config['complexity']['space'], sep='')

strURL = 'https://docs.oracle.com/cd/E56133_01/latest/javadocs/oracle/pgx/api/Analyst.html#'
for analyst in config['analyst']:
    print('- **Javadoc:** [Analyst#', analyst['analyst_method'], '](', strURL, analyst['link'], ')', sep='')
print()

print(config['definition'], sep='')
print()

print('## Signature')
print()

print('| Input Argument | Type | Comment |')
print('| :--- | :--- | :---|')
for arg_in in config['signature']['arguments']['in']:
    print('| `', arg_in['name'], '` | ', arg_in['type'], ' | ', arg_in['description'], ' |')




