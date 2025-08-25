// ForgeRegistries.ITEMS.getKey(itemRegistryObject.get())
// vs
// itemRegistryObject.getId()
// AND
// itemRegistryObject.getId().getPath()
// vs
// ForgeRegistries.ITEMS.getKey(itemRegistryObject.get()).getPath()

// In RegistryObject with:
ResourceLocation name;
ResourceKey<T> key;

RegistryObject<U> create(final ResourceLocation name, IForgeRegistry<> registry){
	return new RegistryObject<U>(name, registry);
}

RegistryObject<U> create(final ResourceLocation name, final ResourceKey<?> registryKey, String modid){
	return new RegistryObject<>(name, registryKey.location, modid, false)
}

RegistryObject<U> create(final ResourceLocation name, final ResourceLocation registryName, String modid){
	return new RegistryObject<>(name, registryName, modid, false)
}


RegistryObject(ResourceLocation name, IForgeRegistry<?> registry){
	this.name = name;
	this.key = (ResourceKey<T>)
}

RegistryObject(ResourceLocation name, ResourceLocation registryName, String modid, boolean optionalRegistry){
	this.name = name;
	this.key = ResourceKey.create(ResourceKey.createRegistryKey(registryName), name);
}

public ResourceLocation getId(){
	return this.name;
}

public ResourceKey<T> getKey(){
	return this.key
}

// In ForgeRegistries:
ResourceKey<Registry<Block>>  BLOCKS  = key("block");

private static <T> ResourceKey<Registry<T>> key(String name){
    return ResourceKey.createRegistryKey(new ResourceLocation(name));
}




// In DeferredRegister with:
String modid
ResourceKey<?> registryKey
Map<RegistryObject<T>, Supplier<T>> entries = new LinkedHashMap<>();

public static <B> DeferredRegister<B> create(IForgeRegistry<B> reg, String modid)
    {
        return new DeferredRegister<>(reg, modid);
    }

private DeferredRegister(IForgeRegistry<T> reg, String modid){
    this(reg.getRegistryKey(), modid, false);
}

private DeferredRegister(
	ResourceKey<? extends Registry<T>> registryKey,
	String modid,
	boolean optionalRegistry
){
	this.registryKey = registryKey;
	this.modid = modid;
	this.optionalRegistry = optionalRegistry;
}

RegistryObject<I> register(String name, Supplier<I> sup){
	final ResourceLocation key = new ResourceLocation(modid, name)
	ret = RegistryObject.create(key, this.registryKey, this.modid)
	entries.putIfAbsent((RegistryObject<T>) ret, sup)

	return ret
}

// In ResourceKey:
public static <T> ResourceKey<Registry<T>> createRegistryKey(
	ResourceLocation location
){
	return create(BuiltInRegistries.ROOT_REGISTRY_NAME, location);
}

private static <T> ResourceKey<T> create(
	ResourceLocation pRegistryName,
	ResourceLocation pLocation
){
	return (ResourceKey<T>) VALUES.computeIfAbsent(
		new ResourceKey.InternKey(pRegistryName, pLocation),
		(p_258225_) -> 
			{return new ResourceKey(p_258225_.registry, p_258225_.location);}
	);
}

private ResourceKey(ResourceLocation pRegistryName, ResourceLocation pLocation) {
  this.registryName = pRegistryName;
  this.location = pLocation;
}

// In ResourceLocation with
String path
String namespace

ResourceLocation(String namespace, String path){
	this.namespace = namespace;
	this.path = path;
}

ResourceLocation(String location){
	this(decompose(location, ':'));
}

protected static String[] decompose(String Location, char separator) {
  String[] astring = new String[]{"minecraft", location};
  int i = location.indexOf(separator);
  if (i >= 0) {
     astring[1] = location.substring(i + 1);
     if (i >= 1) {
        astring[0] = location.substring(0, i);
     }
  }

  return astring;
}


String getPath(){
	return this.path;
}

String getNamespace(){
	return this.path;
}

//////////////////////////////////////
DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,AppleSaplings.MODID);
// produces
this(ForgeRegistries.BLOCKS.getRegistryKey(), "applesaplings", false);
// produces
DeferredRegister(
	ForgeRegistries.BLOCKS.getRegistryKey(),
	"applesaplings",
	false
)
// produces
DeferredRegister(
	ForgeRegistries.key("block"),
	"applesaplings",
	false
)
// produces
DeferredRegister(
	ResourceKey.createRegistryKey(new ResourceLocation("block")),
	"applesaplings",
	false
)
// produces
DeferredRegister(
	ResourceKey.createRegistryKey(ResourceLocation["minecraft", "block"]),
	"applesaplings",
	false
)
// produces
DeferredRegister(
	ResourceKey.create(
		BuiltInRegistries.ROOT_REGISTRY_NAME,
		new ResourceLocation(
			this.namespace = "minecraft",
			this.path = "block"
		),
	"applesaplings",
	false
)
// produces
DeferredRegister(
	ResourceKey.create(
		ResourceLocation("root"),
		new ResourceLocation(
			this.namespace = "minecraft",
			this.path = "block"
		)
	),
	"applesaplings",
	false
)
// produces
DeferredRegister(
	ResourceKey.create(
		new ResourceLocation(
			this.namespace = "minecraft",
			this.path = "root"
		),
		new ResourceLocation(
			this.namespace = "minecraft",
			this.path = "block"
		)
	),
	"applesaplings",
	false
)
// produces
DeferredRegister(
	VALUES.computeIfAbsent(
		new ResourceKey.InternKey(
			ResourceLocation(
				this.namespace = "minecraft",
				this.path = "root"
			),
			ResourceLocation(
				this.namespace = "minecraft",
				this.path = "block"
			)
		),
		(p_258225_) -> 
			{return new ResourceKey(p_258225_.registry, p_258225_.location);}
	),
	"applesaplings",
	false
)
//produces
DeferredRegister(
	VALUES.computeIfAbsent(
		new ResourceKey{
  			this.registryName = ResourceLocation(
				this.namespace = "minecraft",
				this.path = "root"
			);
  			this.location = ResourceLocation(
				this.namespace = "minecraft",
				this.path = "block"
			);
		},
		(p_258225_) -> 
			{return new ResourceKey(p_258225_.registry, p_258225_.location);}
	),
	"applesaplings",
	false
)
// produces
DeferredRegister(
	new ResourceKey{
		this.registryName = ResourceLocation(
			this.namespace = "minecraft",
			this.path = "root"
			);
		this.location = ResourceLocation(
			this.namespace = "minecraft",
			this.path = "block"
		);
	},
	"applesaplings",
	false
)
// produces
this.registryKey = 
	new ResourceKey{
		this.registryName = ResourceLocation(
			this.namespace = "minecraft",
			this.path = "root"
			);
		this.location = ResourceLocation(
			this.namespace = "minecraft",
			this.path = "block"
		)
	}
this.modid = "applesaplings";
this.optionalRegistry = false;

// NEXT //

MODBLOCKS.register(blockID, block)
// produces
ResourceLocation key = new ResourceLocation(modid, blockID);
ret = RegistryObject.create(key, registryKey = BLOCKS, this.modid)
//produces
RegistryObject.create(
	new ResourceLocation(modid, blockID),
	registryKey = BLOCKS,
	this.modid
)
//produces
new RegistryObject<>(
	new ResourceLocation(modid, blockID),
	registryKey.location,
	modid, false
)
//produces
new RegistryObject<>(
	ResourceLocation(
		this.namespace = modid,
		this.path = blockID
	)
	ResourceLocation(
		this.namespace = "minecraft",
		this.path = "block"
	),
	modid, false
)
//produces
RegistryObject<>{
	this.name = ResourceLocation(
		this.namespace = modid,
		this.path = blockID
	);
	this.key = ResourceKey.create(
		ResourceKey.createRegistryKey(
			ResourceLocation(
				this.namespace = "minecraft",
				this.path = "block"
			),
			ResourceLocation(
				this.namespace = modid,
				this.path = blockID
			)

		)
	)
}
//produces
RegistryObject<>{
	this.name = ResourceLocation(
		this.namespace = modid,
		this.path = blockID
	);
	this.key = ResourceKey.create(		
		ResourceKey{
			this.registryName = ResourceLocation(
				this.namespace = "minecraft",
				this.path = "root"
				);
			this.location = ResourceLocation(
				this.namespace = "minecraft",
				this.path = "block"
			)
		},
		ResourceLocation(
			this.namespace = modid,
			this.path = blockID
		)
	)
}
//produces
RegistryObject<>{
	this.name = ResourceLocation(
		this.namespace = modid,
		this.path = blockID
	);
	this.key = ResourceKey.create(		
		ResourceLocation(
			this.namespace = "minecraft",
			this.path = "block"
		),
		ResourceLocation(
			this.namespace = modid,
			this.path = blockID
		)
	)
}
//produces
RegistryObject<>{
	this.name = ResourceLocation(
		this.namespace = modid,
		this.path = blockID
	);
	this.key = ResourceKey{		
		this.registryName = ResourceLocation(
			this.namespace = "minecraft",
			this.path = "block"
		),
		this.location = ResourceLocation(
			this.namespace = modid,
			this.path = blockID
		)
	)
}

// NEXT //

item.getId()
// produces
ResourceLocation(
		this.namespace = modid,
		this.path = itemID
	);

// NEXT //

item.getId().getPath()
// produces
blockID

// NEXT //

ForgeRegistries.ITEMS.getKey(itemRegistryObject.get())
// produces
ResourceLocation(
	this.namespace = modid,
	this.path = itemID
)

// NEXT //

ForgeRegistries.ITEMS.getKey(itemRegistryObject.get())
// produces
ResourceLocation(
	this.namespace = modid,
	this.path = itemID
)