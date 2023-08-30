package theGamblignant.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theGamblignant.VriskaMod;

import static theGamblignant.VriskaMod.makeCardPath;

public class VimMiracle extends AbstractVriskaCard {

    // TEXT DECLARATION

    public static final String ID = VriskaMod.makeID(VimMiracle.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 0;
    private static final int MAGIC = 4;
    private static final int MAGIC_ADDEND = 8;

    // /STAT DECLARATION/


    public VimMiracle() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = MAGIC;
        this.retain = true;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            upgradeMagicNumber(MAGIC_ADDEND);
            initializeDescription();
        }
    }
}
